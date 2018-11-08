package com.sununiq.handler

import com.sununiq.entity.Table
import com.sununiq.exception.YbatisException
import com.sununiq.handler.token.TokenParser
import com.sununiq.util.getValue
import com.sununiq.util.transform2String
import org.w3c.dom.Element
import org.w3c.dom.Node

class ForEachProcessor : NodeProcessor {

    override fun process(table: Table, node: Node, tokenParser: TokenParser): String {
        val element = node as Element
        val listKey = element.getAttribute("list")
        val varKey = element.getAttribute("var")
        val split = element.getAttribute("split")

        val list = getValue(listKey, table) ?: return ""

        val nodeBody = transform2String(element.childNodes)
        val result = StringBuilder(128)
        if (list is Collection<*>) {
            list.forEach {
                result.append(tokenParser.parse(mapOf(varKey to it!!), nodeBody))
                        .append(split)
            }
        } else {
            throw YbatisException("Failed to find list parameters in source data.")
        }

        return result.substring(0, result.length - split.length).toString()
    }
}
package com.sununiq.handler

import com.sununiq.entity.Table
import com.sununiq.handler.token.TokenParser
import com.sununiq.util.getResourceAsString
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File

/**
 * 处理外部引用文件
 */
class RefProcessor : NodeProcessor {

    override fun process(table: Table, node: Node, tokenParser: TokenParser): String {
        val element = node as Element
        val path = element.getAttribute("path")
        val parseResult = tokenParser.parse(table, path)

        val refFilePath = parseResult.replace("classpath:", "")
        // TODO 加判断
//        if (!File(refFilePath).exists()) {
//            return ""
//        }
        return getResourceAsString(refFilePath)
    }
}
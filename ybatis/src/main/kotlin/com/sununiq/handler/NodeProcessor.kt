package com.sununiq.handler

import com.sununiq.entity.Table
import com.sununiq.handler.token.TokenParser
import org.w3c.dom.Node

interface NodeProcessor {

    fun process(table: Table, node: Node, tokenParser: TokenParser): String
}
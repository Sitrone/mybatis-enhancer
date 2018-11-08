package com.sununiq.loader

import com.sununiq.Ybatis
import com.sununiq.domain.User
import com.sununiq.entity.Table
import com.sununiq.handler.TableMapper
import com.sununiq.handler.token.TokenParser
import com.sununiq.util.getResourceAsString
import com.sununiq.util.parseXml
import com.sununiq.util.println
import com.sununiq.util.transform2String
import org.apache.commons.lang3.StringEscapeUtils
import org.junit.Test
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.util.*

class YbatisLoaderTest {

    private val target = """
            <insert id="insertSelective"
                keyProperty="@{primaryColumns.0.jdbcName}" useGeneratedKeys="true"
                parameterType="@{domainClassName}">
                insert into @{tableName}
                (
                <enhancer:foreach list="primaryColumns" var="elem"
                    split=",">
                    `@{elem.jdbcName}`
                </enhancer:foreach>
                <enhancer:foreach list="normalColumns" var="elem">
                    <if test="@{elem.javaName} != null">
                        ,`@{elem.jdbcName}`
                    </if>
                </enhancer:foreach>
                )
                values (
                <enhancer:foreach list="primaryColumns" var="elem">
                    #{@{elem.javaName}}
                </enhancer:foreach>
                <enhancer:foreach list="normalColumns" var="elem">
                    <if test="@{elem.javaName} != null">
                        , #{@{elem.javaName}}
                    </if>
                </enhancer:foreach>
                )
            </insert>
        """.trimIndent()

    private val tokenParser = TokenParser()

    @Test
    fun testParseSpecialTag() {
        val document = parseXml(getResourceAsString("ybatis.xml"))
//        val document = parseXml(target)
        val table = initTable()
        val element = document.documentElement
        doHandleSpecialNode(element, table, document)

        val parse = tokenParser.parse(table, transform2String(element))


        StringEscapeUtils.unescapeXml(parse).println()
    }

    private fun initTable(): Table {
        val tableMapper = TableMapper()
        return tableMapper.mapper2Table(User::class.java, "com.sununiq.domain")
    }

    /**
     * 递归 dfs遍历
     */
    private fun doHandleSpecialNode(node: Node, table: Table, document: Document) {
        if (node.hasChildNodes()) {
            for (i in 0 until node.childNodes.length) {
                doHandleSpecialNode(node.childNodes.item(i), table, document)
            }
        }

        val parentNode = node.parentNode
        Ybatis.assistHandlers.forEach {
            if (node.nodeName == it.key) {
                val parseResult = it.value.process(table, node, tokenParser)
                val textNode = document.createTextNode(parseResult)
                parentNode.replaceChild(textNode, node)
            }
        }
    }

    private fun doHandleNormalNode(node: Node, table: Table, document: Document) {
        doHandleAttribute(node, table)

        if (node.hasChildNodes()) {
            for (i in 0 until node.childNodes.length) {
                doHandleNormalNode(node.childNodes.item(i), table, document)
            }
        } else {
            val parentNode = node.parentNode
            val source = transform2String(node)
            val parse = tokenParser.parse(table, source)
            parentNode.replaceChild(document.createTextNode(parse), node)
        }
    }

    private fun doHandleAttribute(node: Node, table: Table) {
        if (!node.hasAttributes()) return
        val attributes = node.attributes
        for (i in 0 until attributes.length) {
            val item = attributes.item(i)
            item.nodeValue = tokenParser.parse(table, item.nodeValue)
        }
    }

    /**
     * dfs遍历处理自定义的标签
     */
    private fun doHandleSpecialNode1(node: Node, table: Table, document: Document) {
        val stack = Stack<Node>()
        stack.push(node)

        while (stack.isNotEmpty()) {
            val curNode = stack.pop()
            if (curNode.hasChildNodes()) {
                for (i in 0 until curNode.childNodes.length) {
                    stack.push(curNode.childNodes.item(i))
                }
            }

            if (Ybatis.assistHandlers.containsKey(curNode.nodeName)) {
                val parentNode = curNode.parentNode
                Ybatis.assistHandlers.entries.forEach {
                    if (curNode.nodeName == it.key) {
                        val parseResult = it.value.process(table, curNode, tokenParser)
                        val textNode = document.createTextNode(parseResult)
                        parentNode.replaceChild(textNode, curNode)
                    }
                }
            }
        }
    }
}
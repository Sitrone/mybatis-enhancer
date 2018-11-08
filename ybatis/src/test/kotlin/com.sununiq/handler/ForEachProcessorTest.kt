package com.sununiq.handler

import com.sununiq.entity.Column
import com.sununiq.entity.Table
import com.sununiq.handler.token.TokenParser
import com.sununiq.util.parseXml
import com.sununiq.util.println
import com.sununiq.util.removeChildNodes
import com.sununiq.util.transform2String
import org.apache.commons.lang3.StringEscapeUtils
import org.apache.commons.lang3.StringUtils
import org.junit.Test
import org.w3c.dom.Node

class ForEachProcessorTest {

    @Test
    fun testForEach() {
        val target = """
                <enhancer:foreach list="allColumns" var="elem">
                    <if test="@{elem.javaName} != null">
                        and `@{elem.jdbcName}` = #{@{elem.javaName}}
                    </if>
                </enhancer:foreach>
        """.trimIndent()

        val column1 = Column("id", "id", "long", "long")
        val column2 = Column("age", "age", "long", "long")
        val columns = listOf(column1, column2)

        val table = Table("user", "utf-8", "", "",
                          columns, columns, columns, Column::class.java, "column",
                          "com.sununiq.Column", "", mutableMapOf()
        )

        val nodeProcessor = ForEachProcessor()
        val node = parseXml(target).documentElement as Node
        val result = nodeProcessor.process(table, node, TokenParser())
        println(result)
    }

    @Test
    fun processNode() {
        val target = """
            <select id="selectEntity" parameterType="@{domainClassName}"
                resultMap="BaseResultMap">
                select
                <include refid="Base_Column_List" />
                from @{tableName}
                where 1 = 1
                <enhancer:foreach list="allColumns" var="elem">
                    <if test="@{elem.javaName} != null">
                        and `@{elem.jdbcName}` = #{@{elem.javaName}}
                    </if>
                </enhancer:foreach>
                limit 1
            </select>
        """.trimIndent()

        val document = parseXml(target)
        val element = document.documentElement
        element.tagName.println()
//        element.textContent.println()
        val content = transform2String(element.childNodes)

        transform2String(document).println()

        removeChildNodes(element)

        transform2String(document).println()

        val textNode = document.createTextNode(content)

        element.appendChild(textNode)

        transform2String(document).println()
        StringEscapeUtils.unescapeXml(transform2String(document)).println()
    }

    @Test
    fun testHandleAttrubute() {
        val target = """
            <select id="selectEntity" parameterType="@{domainClassName}"
                resultMap="BaseResultMap">
                select
                <include refid="Base_Column_List" />
                from @{tableName}
                where 1 = 1
                <enhancer:foreach list="allColumns" var="elem">
                    <if test="@{elem.javaName} != null">
                        and `@{elem.jdbcName}` = #{@{elem.javaName}}
                    </if>
                </enhancer:foreach>
                limit 1
            </select>
        """.trimIndent()

        val document = parseXml(target)
        val element = document.documentElement
        val attributes = element.attributes

    }

    @Test
    fun testHandleWithBracket() {
        val target = """
            <insert id="insertBatch"
                keyProperty="@{primaryColumns.0.jdbcName}" useGeneratedKeys="true"
                parameterType="java.util.List">
                insert into @{tableName}
                (<include refid="Base_Column_List" />)
                values
                <foreach collection="list" index="index" item="item"
                    separator=",">
                    (
                    <enhancer:foreach list="allColumns" var="elem"
                        split=",">
                        #{item.@{elem.javaName}}
                    </enhancer:foreach>
                    )
                </foreach>
            </insert>
        """.trimIndent()

        val document = parseXml(target)
        val element = document.documentElement
        val attributes = element.attributes

        for (i in 0 until element.childNodes.length) {
            if (element.childNodes.item(i).nodeType == Node.TEXT_NODE) {
                i.println()
                transform2String(element.childNodes.item(i)).println()
            }
        }
    }
}
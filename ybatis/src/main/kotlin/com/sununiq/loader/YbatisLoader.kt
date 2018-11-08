package com.sununiq.loader

import com.sununiq.Ybatis
import com.sununiq.entity.Table
import com.sununiq.handler.TableMapper
import com.sununiq.handler.token.TokenParser
import com.sununiq.util.*
import org.apache.commons.lang3.StringEscapeUtils
import org.apache.ibatis.builder.xml.XMLMapperBuilder
import org.apache.ibatis.logging.LogFactory
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactory
import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.ByteArrayInputStream
import java.util.*

class YbatisLoader(
        val sqlSessionFactory: SqlSessionFactory,
        val templatePath: String,
        val mapperLocations: String
) {
    private val log = LogFactory.getLog(YbatisLoader::class.java)

    private val templateString = getResourceAsString(templatePath)

    private val mappers = LinkedHashMap<Table, String>()

    private val tableMapper = TableMapper()

    private val tokenParser = TokenParser()

    fun build() {
        if (mappers.isEmpty()) return
        val configuration = sqlSessionFactory.configuration

        val isOutput = Ybatis.outputLocation != null
        mappers.forEach { t, s ->
            if (isOutput) {
                log.debug("Auo generated file is write to ${Ybatis.outputLocation} ${t.domainClassName}.xml")
                write2File(s, Ybatis.outputLocation!!, t.domainClassName + ".xml")
            }

            log.debug(">>  Rebuilding ${t.domainClassName}Mapper.xml")
            rebuildMapperFile(s, configuration)
        }
    }

    private fun rebuildMapperFile(source: String, config: Configuration) {
        val mapperStream = ByteArrayInputStream(source.toByteArray())

        XMLMapperBuilder(mapperStream, config, source, config.sqlFragments)
                .apply {
                    parse()
                }
    }

    fun add(package_: String) {
        loadClasses(package_).forEach {
            add(it)
        }
    }

    fun add(clazz: Class<*>) {
        val table = tableMapper.mapper2Table(clazz, mapperLocations)
        if (Class.forName(table.mapperClassName) != null) {
            val mapper = template2Mapper(table)
            val putResult = mappers.putIfAbsent(table, mapper)
            if (putResult != null) {
                log.debug(">>  Loading " + clazz.name)
            }
        }
    }

    private fun template2Mapper(table: Table): String {
        val document = parseXml(templateString)
        val element = document.documentElement

        // 先处理自定义的特殊标签
        doHandleSpecialNode(element, table, document)

        // 再处理通用标签
        val result = doHandleNormalNode(element, table, document)

        return StringEscapeUtils.unescapeXml(result)
    }

    /**
     * dfs遍历处理自定义的标签
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

    private fun doHandleNormalNode(node: Node, table: Table, document: Document): String {
        return tokenParser.parse(table, transform2String(node, document))
    }
}
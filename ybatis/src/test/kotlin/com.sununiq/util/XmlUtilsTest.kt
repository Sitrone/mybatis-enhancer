package com.sununiq.util

import org.junit.Test
import org.w3c.dom.Element
import org.w3c.dom.traversal.DocumentTraversal
import org.w3c.dom.traversal.NodeFilter
import org.w3c.dom.traversal.TreeWalker

class XmlUtilsTest {

    private val target = """
        <select id="userDefined" resultType="int">
          select 1
          <user id= "5">
          </user>
        </select>
    """.trimIndent()

    @Test
    fun testParseSingleXml() {
        val document = parseXml(target)
        val root = document.documentElement

        root.hasChildNodes().println()
    }

    @Test
    fun testTraverse() {
        val document = parseXml(getResourceAsString("ybatis.xml"))
        val traversal = document as DocumentTraversal
        val walker = traversal.createTreeWalker(document.documentElement,
                                                NodeFilter.SHOW_ELEMENT, null, true)

        traverseLevel(walker, "")
    }

    private fun traverseLevel(walker: TreeWalker, indent: String) {
        val parend = walker.currentNode
        println(indent + "- " + (parend as Element).tagName)

        var n = walker.firstChild()
        while (n != null) {
            traverseLevel(walker, indent + '\t')
            n = walker.nextSibling()
        }
        walker.currentNode = parend
    }
}
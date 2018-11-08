package com.sununiq.xml

import com.sununiq.util.getResourceAsString
import com.sununiq.util.parseXml
import com.sununiq.util.println
import com.sununiq.util.transform2String
import org.junit.Test
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.util.regex.Pattern


class DocumentKtTest {

    @Test
    fun testJdkParser() {
        val xmlSource = "ybatis.xml"
        val doc = parseXml(getResourceAsString(xmlSource))
        val deleteList = doc.getElementsByTagName("ybatis:foreach")

        for (i in 0 until deleteList.length) {
            val element = deleteList.item(i) as Element
            val content = element.textContent

            val transform2String = transform2String(element)
        }

//        val element = doc.documentElement
//        doSomething(element)


        transform2String(doc).println()
    }

    @Test
    fun testHandlerResultMap() {
        val xmlSource = "ybatis.xml"
        val doc = parseXml(getResourceAsString(xmlSource))
        val resultMap = doc.getElementsByTagName("resultMap")


    }

    @Test
    fun testRegex() {
        var html = "<a href='myurl?id=1123'>myurl</a>\n" +
                "<a href='myurl2?id=2123'>myurl2</a>\n" +
                "<a href='myurl3?id=3123'>myurl3</a>"
        html = html.replace("id=(\\w+)'?".toRegex(), "productId=$1'")

        replaceAndLookupIds(html).println()
    }

    fun replaceAndLookupIds(html: String): String {
        val newHtml = StringBuffer()
        val p = Pattern.compile("id=(\\w+)'?")
        val m = p.matcher(html)
        while (m.find()) {
            val id = m.group(1)
            val newId = 5
            val rep = "productId=$newId'"
            m.appendReplacement(newHtml, rep)
        }
        m.appendTail(newHtml)
        return newHtml.toString()
    }

    fun doSomething(node: Node) {
        // do something with the current node instead of System.out
        System.out.println(node.nodeName)
        for (i in 0..node.attributes.length) {
            node.attributes.item(i).println()
        }

        val nodeList = node.childNodes
        for (i in 0 until nodeList.length) {
            val currentNode = nodeList.item(i)
            if (currentNode.nodeType == Node.ELEMENT_NODE) {
                //calls this method for all the children which is Element
                doSomething(currentNode)
            }
        }
    }
}
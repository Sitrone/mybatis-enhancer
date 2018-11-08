package com.sununiq.util

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import java.io.File
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


fun parseXml(file: File): Document {
    val builder = initDocumentBuilder()
    return builder.parse(file)
}

fun parseXml(source: String): Document {
    val builder = initDocumentBuilder()
    ByteArrayInputStream(source.toByteArray(StandardCharsets.UTF_8)).use {
        return builder.parse(it)
    }
}

fun removeChildNodes(node: Node) {
    while (node.hasChildNodes()) {
        node.removeChild(node.firstChild)
    }
}

fun transform2String(nodeList: NodeList): String {
    val sw = StringWriter()
    buildTransformer()
            .apply {
                for (i in 0 until nodeList.length) {
                    transform(DOMSource(nodeList.item(i)), StreamResult(sw))
                }
            }

    return sw.toString()
}

fun transform2String(node: Node): String {
    val sw = StringWriter()

    buildTransformer().apply {
        transform(DOMSource(node), StreamResult(sw))
    }

    return sw.toString()
}

fun transform2String(node: Node, document: Document): String {
    val sw = StringWriter()

    buildTransFormer2Xml(document).apply {
        transform(DOMSource(node), StreamResult(sw))
    }

    return sw.toString()
}

private fun buildTransFormer2Xml(document: Document): Transformer {
    val transformer = buildTransformer()
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")

    val domImpl = document.implementation
    val doctype = domImpl.createDocumentType("mapper",
            "-//mybatis.org//DTD Mapper 3.0//EN",
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd")
    transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.publicId)
    transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.systemId)
    return transformer
}

private fun buildTransformer(): Transformer {
    val tf = TransformerFactory.newInstance()
    val transformer = tf.newTransformer()
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
    transformer.setOutputProperty(OutputKeys.METHOD, "xml")
    transformer.setOutputProperty(OutputKeys.INDENT, "yes")
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
    return transformer
}

fun initDocumentBuilder(): DocumentBuilder {
    val factory = DocumentBuilderFactory.newInstance()
    factory.setFeature("http://xml.org/sax/features/namespaces", false)
    factory.setFeature("http://xml.org/sax/features/validation", false)
    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false)
    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)

    return factory.newDocumentBuilder()
}
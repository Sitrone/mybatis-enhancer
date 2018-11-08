package com.sununiq.util

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * 读取jar包里面的额文件不能用getFile()，只能用getInputStream()
 */
fun getResourceAsString(resource: String): String {
    getClass().getResourceAsStream(resource).use {
        return it.bufferedReader(StandardCharsets.UTF_8).readText()
    }
}

private fun getClass() = Thread.currentThread().contextClassLoader

fun write2File(source: String, path: String, name: String) {
    val tempPath = if (path.endsWith(File.separatorChar)) path else path + File.separator
    if (!File(path).exists()) {
        Files.createDirectories(Paths.get(path))
    }

    val filePath = tempPath + name

    write2File(File(filePath), source)
}

fun write2File(file: File, source: String) {
    if (!file.exists()) {
        Files.createFile(file.toPath())
    }

    BufferedWriter(FileWriter(file))
            .use {
                it.write(source)
            }
}
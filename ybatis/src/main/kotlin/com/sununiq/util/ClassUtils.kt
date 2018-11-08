package com.sununiq.util

import java.io.File
import java.util.*


/**
 * 遍历获取给定package下面的所有类
 * 更全面的实现方式可以参考spring
 */
fun loadClasses(package_: String): List<Class<*>> {
    val classLoader = Thread.currentThread().contextClassLoader!!
    val path = package_.replace('.', '/')

    val resources = classLoader.getResources(path)
    val dirs = ArrayList<File>()
    while (resources.hasMoreElements()) {
        val resource = resources.nextElement()
        dirs.add(File(resource.file))
    }

    return ArrayList<Class<*>>()
            .apply {
                dirs.forEach {
                    addAll(findClasses(it, package_))
                }
            }
}

private fun findClasses(directory: File, packageName: String): List<Class<*>> {
    val classes = ArrayList<Class<*>>()
    if (!directory.exists()) {
        return classes
    }

    val files = directory.listFiles()
    for (file in files!!) {
        if (file.isDirectory) {
            assert(!file.name.contains("."))
            classes.addAll(findClasses(file, """$packageName.${file.name}"""))
        } else if (file.name.endsWith(".class")) {
            classes.add(Class.forName("""$packageName.${file.name.substring(0, file.name.length - 6)}"""))
        }
    }
    return classes
}
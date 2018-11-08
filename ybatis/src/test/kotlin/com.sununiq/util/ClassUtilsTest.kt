package com.sununiq.util

import org.junit.Test

class ClassUtilsTest {

    @Test
    fun testLoadPackage() {
        for (clazz in loadClasses("com.sununiq")) {
            clazz.name.println()
        }

        (1 until 2).forEach {
            it.println()
        }
    }
}
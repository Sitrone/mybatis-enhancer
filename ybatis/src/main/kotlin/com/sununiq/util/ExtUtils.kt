package com.sununiq.util

fun <T> T?.println() = this?.let { println(it) }
package com.sununiq.util

import com.sununiq.exception.YbatisException

fun assertNotEmpty(collection: Collection<*>, desc: String) {
    if (collection.isEmpty()) {
        throw YbatisException(desc)
    }
}
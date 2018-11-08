package com.sununiq.exception

import java.lang.RuntimeException

class YbatisException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(throwable: Throwable) : super(throwable)
    constructor(message: String?, throwable: Throwable) : super(message)
}
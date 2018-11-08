package com.sununiq.wrapper

interface Wrapper<T> {
    fun wrapping(t: T): String
}
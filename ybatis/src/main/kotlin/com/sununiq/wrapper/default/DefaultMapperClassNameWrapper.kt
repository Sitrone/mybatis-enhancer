package com.sununiq.wrapper.default

import com.sununiq.wrapper.Wrapper


class DefaultMapperClassNameWrapper : Wrapper<String> {
    override fun wrapping(t: String): String = t + "Mapper"
}
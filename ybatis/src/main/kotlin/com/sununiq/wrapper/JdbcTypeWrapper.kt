package com.sununiq.wrapper

import com.sununiq.Ybatis

class JdbcTypeWrapper : Wrapper<Class<*>> {

    override fun wrapping(t: Class<*>): String = Ybatis.jdbcTypeWrappers[t].toString()
}
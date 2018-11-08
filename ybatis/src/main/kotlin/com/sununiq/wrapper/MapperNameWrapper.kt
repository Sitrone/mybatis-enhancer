package com.sununiq.wrapper

import com.sununiq.Ybatis

class MapperNameWrapper : Wrapper<Class<*>> {

    override fun wrapping(t: Class<*>): String {
        var tableName = t.simpleName
        Ybatis.mapperNameWrappers.forEach {
            tableName = it.wrapping(tableName)
        }
        return tableName
    }

}
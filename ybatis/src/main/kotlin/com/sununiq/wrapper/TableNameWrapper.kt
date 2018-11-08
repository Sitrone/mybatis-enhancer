package com.sununiq.wrapper

import com.sununiq.Ybatis
import com.sununiq.util.getAnnotation
import com.sununiq.util.isAnnotation
import javax.persistence.Table

class TableNameWrapper : Wrapper<Class<*>> {

    override fun wrapping(t: Class<*>): String {
        var tableName = t.simpleName
        var isUseSelfDefinedWrapper = true

        if (isAnnotation(t, Table::class.java)) {
            val table = getAnnotation(t, Table::class.java)!!
            tableName = table.name
            if (tableName.isNotBlank()) {
                isUseSelfDefinedWrapper = false
            }
        }

        if (isUseSelfDefinedWrapper) {
            Ybatis.jdbcNameWrappers.forEach {
                tableName = it.wrapping(tableName)
            }
        }

        return tableName
    }

}
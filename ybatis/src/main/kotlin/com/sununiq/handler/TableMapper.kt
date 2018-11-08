package com.sununiq.handler

import com.sununiq.entity.Column
import com.sununiq.entity.Table
import com.sununiq.util.assertNotEmpty
import com.sununiq.util.getFieldsExceptStatic
import com.sununiq.util.isAnnotation
import com.sununiq.wrapper.*
import java.beans.Transient
import java.lang.reflect.Field
import java.util.*
import javax.persistence.Id

class TableMapper(
        val jdbcNameWrapper: Wrapper<Field> = JdbcNameWrapper(),
        val jdbcTypeWrapper: Wrapper<Class<*>> = JdbcTypeWrapper(),
        val tableNameWrapper: Wrapper<Class<*>> = TableNameWrapper(),
        val mapperNameWrapper: Wrapper<Class<*>> = MapperNameWrapper()
) {

    fun mapper2Table(domainClass: Class<*>, mapperLocations: String): Table {
        val fields = getFieldsExceptStatic(domainClass)
        val primaryColumns = ArrayList<Column>(fields.size)
        val normalColumns = ArrayList<Column>(fields.size)
        val allColumns = ArrayList<Column>(fields.size)

        fields.filterNot {
            isAnnotation(it, Transient::class.java)
        }.forEach {
            if (isAnnotation(it, Id::class.java)) {
                primaryColumns.add(mapper2Column(it))
            } else {
                normalColumns.add(mapper2Column(it))
            }
            allColumns.add(mapper2Column(it))
        }

        assertNotEmpty(primaryColumns, "The entity ${domainClass.name} class need at least one primary field.")
        assertNotEmpty(normalColumns, "The entity ${domainClass.name} class need at least one non primary field.")

        return Table(
                tableName = tableNameWrapper.wrapping(domainClass),
                charset = "utf-8",
                ip = null,
                port = null,
                primaryColumns = primaryColumns,
                normalColumns = normalColumns,
                allColumns = allColumns,
                domainClass = domainClass,
                domainClassName = domainClass.name,
                domainSimpleClassName = domainClass.simpleName,
                mapperClassName = mapperLocations + "." + mapperNameWrapper.wrapping(domainClass),
                parameters = mutableMapOf()
        )
    }

    private fun mapper2Column(field: Field): Column {
        return Column(
                jdbcNameWrapper.wrapping(field),
                field.name,
                jdbcTypeWrapper.wrapping(field.type),
                field.type.simpleName
        )
    }
}
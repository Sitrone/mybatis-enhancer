package com.sununiq.wrapper

import com.sununiq.Ybatis
import com.sununiq.util.getAnnotation
import com.sununiq.util.isAnnotation
import java.lang.reflect.Field
import javax.persistence.Column

/**
 * 字段默认和@Column一样,作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式.
 * 如果使用@Column(name = "fieldName"),则使用注解中的名字作为表字段名
 */
class JdbcNameWrapper : Wrapper<Field> {

    override fun wrapping(field: Field): String {
        var fieldName = field.name
        var isUseSelfDefinedWrapper = true

        if (isAnnotation(field, Column::class.java)) {
            val column = getAnnotation(field, Column::class.java)!!
            fieldName = column.name
            if (fieldName.isNotBlank()) {
                isUseSelfDefinedWrapper = false
            }
        }

        if (isUseSelfDefinedWrapper) {
            Ybatis.jdbcNameWrappers.forEach {
                fieldName = it.wrapping(fieldName)
            }
        }

        return fieldName
    }
}
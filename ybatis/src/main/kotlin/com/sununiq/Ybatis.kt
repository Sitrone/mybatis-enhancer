package com.sununiq

import com.sununiq.handler.ForEachProcessor
import com.sununiq.handler.NodeProcessor
import com.sununiq.handler.RefProcessor
import com.sununiq.wrapper.Wrapper
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Timestamp
import java.util.*

object Ybatis {

    /**
     * Template location
     */
    val templateLocation = "ybatis.xml"

    /**
     * Template matching prefix
     */
    val prefix = "@\\{"

    /**
     * Template matching suffix
     */
    val suffix = "}"


    var outputLocation: String? = null

    val assistHandlers = mutableMapOf<String, NodeProcessor>(
            "enhancer:ref" to RefProcessor(),
            "enhancer:foreach" to ForEachProcessor()
    )

    val tableNameWrappers: List<Wrapper<String>> = ArrayList()

    val mapperNameWrappers: List<Wrapper<String>> = ArrayList()

    val jdbcTypeWrappers: MutableMap<Class<*>, String> = HashMap<Class<*>, String>()
            .apply {
                put(String::class.java, "VARCHAR")
                put(BigDecimal::class.java, "DECIMAL")
                put(BigInteger::class.java, "BIGINT")
                put(Boolean::class.java, "BIT")
                put(Byte::class.java, "TINYINT")
                put(Short::class.java, "SMALLINT")
                put(Int::class.java, "INTEGER")
                put(Long::class.java, "BIGINT")
                put(Float::class.java, "REAL")
                put(Double::class.java, "DOUBLE")
                put(Date::class.java, "DATE")
                put(Timestamp::class.java, "TIMESTAMP")
            }

    val jdbcNameWrappers: List<Wrapper<String>> = ArrayList()
}
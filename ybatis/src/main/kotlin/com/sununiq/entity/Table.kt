package com.sununiq.entity

data class Table(
        val tableName: String,
        val charset: String = "utf-8",
        val ip: String?,
        val port: String?,
        val primaryColumns: List<Column>,
        val normalColumns: List<Column>,
        val allColumns: List<Column>,
        val domainClass: Class<*>,
        val domainSimpleClassName: String,
        val domainClassName: String,
        val mapperClassName: String,
        val parameters: MutableMap<String, Any>
)
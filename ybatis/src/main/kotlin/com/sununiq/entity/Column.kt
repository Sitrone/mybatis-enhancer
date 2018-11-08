package com.sununiq.entity

data class Column(
        val jdbcName: String,
        val javaName: String,
        val jdbcType: String,
        val javaType: String
)
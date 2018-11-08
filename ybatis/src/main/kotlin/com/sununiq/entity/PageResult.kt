package com.sununiq.entity

data class PageResult<T>(
        val total: Long,
        val result: List<T>
)
package com.sununiq.entity

open class Page<T>(
        val start: Long?,
        val end: Long?,
        // order field
        val orderByField: String?,
        // desc, asc
        val orderBy: String = "asc",
        val entity: T?
) {
    constructor() : this(null, null, null, "asc", null)

    companion object {
        fun start(entity: Any, orderBy: String): Page<Any> {
            return start(null, null, entity, orderBy)
        }

        fun start(page: Long?, length: Long?, entity: Any?, orderByField: String?): Page<Any> {
            return start(page, length, entity, orderByField, "asc")
        }

        fun start(page: Long?, length: Long?, entity: Any?, orderByField: String?, orderBy: String): Page<Any> {
            var page_ = page
            var length_ = length
            var start: Long? = null
            var end: Long? = null
            if (page_ != null && length_ != null) {
                if (page_ < 1) page_ = 1L
                if (length_ < 1) length_ = 10L
                start = (page_ - 1) * length_
                end = length_
            } else {
                start = 1L
                end = 10L
            }

            val orderBy_ = if (orderBy == "asc" ) "asc" else "desc"
            return Page(start, end, orderByField, orderBy_, entity)
        }
    }
}
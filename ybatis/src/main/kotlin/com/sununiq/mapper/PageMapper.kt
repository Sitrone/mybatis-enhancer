package com.sununiq.mapper

import com.sununiq.entity.Page

interface PageMapper<T> {

    /**
     * Paging query
     *
     * @param page [Page]
     * @return Search result
     */
    fun selectPage(page: Page<Any>): List<T>
}
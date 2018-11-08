package com.sununiq.handler

import com.sununiq.domain.User
import com.sununiq.util.println
import org.junit.Test

class TableMapperTest {

    @Test
    fun testTableMapper() {
        val tableMapper = TableMapper()
        val table = tableMapper.mapper2Table(User::class.java, "com.sununiq.domain")

        table.println()
    }

}
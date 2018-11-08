package com.sununiq.handler

import com.sununiq.handler.token.TokenParser
import org.junit.Test
import java.util.regex.Pattern
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class TokenParserTest {

    @Test
    fun testParseWithMap() {
        val tokenParser = TokenParser()
        val expect = tokenParser.parse(mapOf("domainSimpleClassName" to "list"),
                                       "classpath:ybbatis-mappers/@{domainSimpleClassName}Mapper.xml")

        assertEquals(expect, "classpath:ybbatis-mappers/listMapper.xml")
    }

    @Test
    fun testPattern() {
        val p = Pattern.compile("""@\{\w+(\.[a-zA-Z0-9]+)*}""")
        assertTrue {
            p.matcher("@{abc.efg.hijk}").matches()
        }

        assertFalse {
            p.matcher("@{abgc...}").matches()
        }
    }
}
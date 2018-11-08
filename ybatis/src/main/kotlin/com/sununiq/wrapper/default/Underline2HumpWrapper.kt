package com.sununiq.wrapper.default

import com.sununiq.wrapper.Wrapper

class Underline2HumpWrapper : Wrapper<String> {
    private val underscore = '_'

    /**
     * 驼峰转下划线
     */
    override fun wrapping(t: String): String {
        val builder = StringBuilder(t.length + 5)
        t.toCharArray().forEachIndexed { i, c ->
            if (c.isUpperCase()) {
                if (i > 0) {
                    builder.append(underscore)
                }
                builder.append(c.toLowerCase())
            } else {
                builder.append(c)
            }
        }

        return builder.toString()
    }

}
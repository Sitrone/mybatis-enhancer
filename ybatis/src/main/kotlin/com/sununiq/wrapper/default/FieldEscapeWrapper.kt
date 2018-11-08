package com.sununiq.wrapper.default

import com.sununiq.wrapper.Wrapper

class FieldEscapeWrapper : Wrapper<String> {
    private val escape = '`'

    override fun wrapping(t: String): String {
        var tempField = t
        if (!tempField.startsWith(escape)) {
            tempField = escape + tempField
        }

        if (!tempField.endsWith(escape)) {
            tempField = tempField + escape
        }

        return tempField
    }

}
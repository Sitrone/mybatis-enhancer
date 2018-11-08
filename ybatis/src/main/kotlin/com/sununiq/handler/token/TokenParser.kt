package com.sununiq.handler.token

import com.sununiq.Ybatis
import com.sununiq.util.getValue
import java.util.regex.Pattern

class TokenParser(
        val openToken: String = Ybatis.prefix,
        val closeToken: String = Ybatis.suffix,
        val split: String = "."
) {
    fun parse(source: Any, target: String): String {
        val m = Pattern.compile("""$openToken\w+(\$split[a-zA-Z0-9]+)*$closeToken""")
                .matcher(target)

        return StringBuffer(target.length)
                .apply {
                    while (m.find()) {
                        val group = m.group()
                        val tempKey = group.subSequence(openToken.length - 1, group.length - closeToken.length)
                                .toString()
                        m.appendReplacement(this, valueWrapper(getValueRecursive(tempKey, source)))
                    }

                    m.appendTail(this)
                }.toString()
    }

    private fun valueWrapper(value: Any?): String {
        return value?.toString() ?: ""
    }

    private fun getValueRecursive(fieldNames: String, obj: Any): Any? {
        val fieldSegments = fieldNames.split(("\\" + split).toRegex())
                .dropLastWhile { it.isBlank() }
                .toTypedArray()

        var target: Any? = obj
        for (segment in fieldSegments) {
            if (target != null) {
                target = getValue(segment, target)
            }
        }
        return target
    }
}

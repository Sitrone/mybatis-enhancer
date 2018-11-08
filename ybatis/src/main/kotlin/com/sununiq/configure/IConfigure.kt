package com.sununiq.configure

import com.sununiq.handler.NodeProcessor
import com.sununiq.wrapper.Wrapper

interface IConfigure {
    fun configWrapper(
            jdbcNameWrappers: MutableList<Wrapper<String>>,
            tableNameWrappers: MutableList<Wrapper<String>>,
            mapperNameWrappers: MutableList<Wrapper<String>>,
            javaTypeWrappers: MutableMap<Class<*>, String>
    )

    fun configNodeProcessor(nodeProcessors: MutableMap<String, NodeProcessor>)
}
package com.sununiq.configure

import com.sununiq.handler.NodeProcessor
import com.sununiq.wrapper.Wrapper
import com.sununiq.wrapper.default.Underline2HumpWrapper
import com.sununiq.wrapper.default.DefaultMapperClassNameWrapper

class DefaultConfigure : IConfigure {
    override fun configWrapper(
            jdbcNameWrappers: MutableList<Wrapper<String>>,
            tableNameWrappers: MutableList<Wrapper<String>>,
            mapperNameWrappers: MutableList<Wrapper<String>>,
            javaTypeWrappers: MutableMap<Class<*>, String>
    ) {
        tableNameWrappers.add(Underline2HumpWrapper())
        jdbcNameWrappers.add(Underline2HumpWrapper())
        mapperNameWrappers.add(DefaultMapperClassNameWrapper())
    }

    override fun configNodeProcessor(nodeProcessors: MutableMap<String, NodeProcessor>) {

    }

}
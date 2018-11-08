package com.sununiq.mapper

interface Mapper<T, K> : SelectMapper<T, K>, InsertMapper<T, K>, UpdateMapper<T, K>, DeleteMapper<T, K>, PageMapper<T> {

}
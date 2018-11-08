package com.sununiq.mapper

interface InsertMapper<T, K> {

    /**
     * Insert entity
     *
     * @param entity
     * Entity
     * @return Modify the quantity
     */
    fun insert(entity: T): Int

    /**
     * Insert entity where the field is not null
     *
     * @param entity
     * Entity
     * @return Modify the quantity
     */
    fun insertSelective(entity: T): Int

    /**
     * Insert entity list
     *
     * @param list
     * Entity list
     * @return Modify the quantity
     */
    fun insertBatch(list: List<T>): Int
}
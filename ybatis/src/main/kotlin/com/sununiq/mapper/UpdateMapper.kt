package com.sununiq.mapper

interface UpdateMapper<T, K> {
    /**
     * Update entity
     *
     * @param entity
     * Entity
     * @return Modify the quantity
     */
    fun update(entity: T): Int

    /**
     * Update entity where the field is not null
     *
     * @param entity
     * Entity
     * @return Modify the quantity
     */
    fun updateSelective(entity: T): Int

    /**
     * Update entity list
     *
     * @param list
     * Entity list
     * @return Modify the quantity
     */
    fun updateBatch(list: List<T>): Int
}
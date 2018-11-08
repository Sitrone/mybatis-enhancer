package com.sununiq.mapper

interface DeleteMapper<T, K> {

    /**
     * Delete entity by condition
     *
     * @param condition
     * Query condition
     * @return Modify the quantity
     */
    fun delete(condition: T): Int

    /**
     * Delete by primary key
     *
     * @param key
     * Primary key
     * @return Modify the quantity
     */
    fun deleteById(key: K): Int

    /**
     * Delete by list of primary key
     *
     * @param list
     * List of primary key
     * @return Modify the quantity
     */
    fun deleteBatch(list: List<K>): Int
}
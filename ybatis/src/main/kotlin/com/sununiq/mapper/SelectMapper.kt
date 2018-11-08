package com.sununiq.mapper

interface SelectMapper<T, K> {
    /**
     * Select entity by primary key
     *
     * @param key
     * Primary key
     * @return Search result
     */
    fun selectById(key: K): T

    /**
     * Select entity by condition
     *
     * @param condition
     * Query condition
     * @return Search result
     */
    fun selectEntity(condition: T): T

    /**
     * Select entity list by condition
     *
     * @param condition
     * Query condition
     * @return Search result
     */
    fun selectList(condition: T): List<T>

    /**
     * Select count by contidion
     *
     * @param condition
     * Query condition
     * @return The number of queries
     */
    fun selectCount(condition: Any): Long


    /**
     * Select primary key by condition
     *
     * @param condition
     * Query condition
     * @return Primary key
     */
    fun selectId(condition: T): K

    /**
     * Select list of primary key by condition
     *
     * @param condition
     * Query condition
     * @return List of primary key
     */
    fun selectIds(condition: T): List<K>
}
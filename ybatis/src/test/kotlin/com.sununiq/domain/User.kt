package com.sununiq.domain

import javax.persistence.Id

data class User(
        @Id
        val id: Int,

        val age: Int
)
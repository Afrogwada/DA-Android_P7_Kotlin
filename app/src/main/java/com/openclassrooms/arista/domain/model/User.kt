package com.openclassrooms.arista.domain.model

data class User(
    val id: Long? = null,
    var name: String,
    var email: String,
    var password: String
)
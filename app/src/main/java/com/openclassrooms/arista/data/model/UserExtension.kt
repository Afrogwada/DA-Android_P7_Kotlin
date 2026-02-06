package com.openclassrooms.arista.data.model

import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.domain.model.User


fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email,
        password = this.password
    )
}

fun User.toEntity(): UserDto {
    return UserDto(
        id = this.id ?: 0,
        name = name,
        email = email,
        password = password
    )
}
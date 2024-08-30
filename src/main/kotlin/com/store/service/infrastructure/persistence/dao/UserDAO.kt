package com.store.service.infrastructure.persistence.dao

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity(value = "user")
data class UserDAO(

    @field:Id
    @field:GeneratedValue
    val id: String? = null,
    val username: String,
    val password: String,
    val roles: List<String>
)

package com.store.service.infrastructure.persistence.repository

import com.store.service.infrastructure.persistence.dao.UserDAO
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface UserRepositoryCrud : CrudRepository<UserDAO, String> {

    fun findByUsername(username: String): UserDAO?
}
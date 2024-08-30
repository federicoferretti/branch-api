package com.store.service.domain.service

import io.micronaut.security.authentication.AuthenticationResponse

interface UserAuthenticator {

    fun validateUser(username: String, password: String): AuthenticationResponse
}

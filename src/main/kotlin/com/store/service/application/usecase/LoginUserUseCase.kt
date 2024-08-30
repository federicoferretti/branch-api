package com.store.service.application.usecase

import com.store.service.domain.exception.AuthenticationFailedException
import com.store.service.domain.service.UserAuthenticator
import com.store.service.infrastructure.security.TokenService
import jakarta.inject.Singleton

@Singleton
class LoginUserUseCase(
    private val userAuthenticator: UserAuthenticator,
    private val tokenService: TokenService
) {

    fun execute(username: String, password: String): String {
        val authResponse = userAuthenticator.validateUser(username, password)

        if (!authResponse.isAuthenticated) {
            throw AuthenticationFailedException("Credenciales invalidas")
        }

        val authentication = authResponse.authentication.get()
        return tokenService.generateToken(authentication)
    }
}
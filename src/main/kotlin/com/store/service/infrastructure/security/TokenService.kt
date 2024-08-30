package com.store.service.infrastructure.security

import com.store.service.domain.exception.TokenGenerationException
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import jakarta.inject.Singleton

@Singleton
class TokenService(
    private val jwtTokenGenerator: JwtTokenGenerator
) {
    fun generateToken(authentication: Authentication): String {
        return jwtTokenGenerator.generateToken(authentication, 3600)
            .orElseThrow { TokenGenerationException("fallo la generación del token") }
    }
}

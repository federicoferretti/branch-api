package com.store.service.infrastructure.security

import com.store.service.domain.exception.TokenGenerationException
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.util.*

internal class TokenServiceTest {

    private val jwtTokenGenerator: JwtTokenGenerator = mock()
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setUp() {
        tokenService = TokenService(jwtTokenGenerator)
    }

    @Test
    fun `generateToken returns valid token when jwtTokenGenerator succeeds`() {
        val authentication: Authentication = mock()

        doReturn(Optional.of("validToken"))
            .`when`(jwtTokenGenerator)
            .generateToken(authentication, 3600)

        val result = tokenService.generateToken(authentication)

        assertEquals("validToken", result)
    }

    @Test
    fun `generateToken throws TokenGenerationException when jwtTokenGenerator fails`() {
        val authentication: Authentication = mock()

        doReturn(Optional.empty<String>())
            .`when`(jwtTokenGenerator)
            .generateToken(authentication, 3600)

        assertThrows(TokenGenerationException::class.java) {
            tokenService.generateToken(authentication)
        }
    }
}

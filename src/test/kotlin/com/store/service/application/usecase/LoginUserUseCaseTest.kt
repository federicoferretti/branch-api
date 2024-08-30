package com.store.service.application.usecase

import com.store.service.domain.exception.AuthenticationFailedException
import com.store.service.domain.service.UserAuthenticator
import com.store.service.infrastructure.security.TokenService
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.AuthenticationResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import java.util.*

class LoginUserUseCaseTest {
    private val userAuthenticator: UserAuthenticator = mock()
    private val tokenService: TokenService = mock()

    private lateinit var loginUserUseCase: LoginUserUseCase

    @BeforeEach
    fun setUp() {
        loginUserUseCase = LoginUserUseCase(userAuthenticator, tokenService)
    }

    @Test
    fun `execute should return token when authentication is successful`() {
        val username = "user"
        val password = "pass"
        val expectedToken = "jwttoken"
        val authentication: Authentication = mock()
        val authenticationResponse: AuthenticationResponse = mock()

        whenever(authenticationResponse.isAuthenticated).thenReturn(true);
        whenever(authenticationResponse.authentication).thenReturn(Optional.of(authentication))

        whenever(userAuthenticator.validateUser(username, password)).thenReturn(authenticationResponse)
        whenever(tokenService.generateToken(authentication)).thenReturn(expectedToken)

        val result = loginUserUseCase.execute(username, password)

        assertEquals(expectedToken, result)
        verify(userAuthenticator).validateUser(username, password)
        verify(tokenService).generateToken(authentication)
    }

    @Test
    fun `execute should throw AuthenticationFailedException when authentication fails`() {
        val username = "user"
        val password = "wrongpass"

        val authenticationResponse: AuthenticationResponse = mock()
        whenever(authenticationResponse.isAuthenticated).thenReturn(false)
        whenever(userAuthenticator.validateUser(username, password)).thenReturn(authenticationResponse)

        val exception = assertThrows<AuthenticationFailedException> {
            loginUserUseCase.execute(username, password)
        }

        assertEquals("Credenciales invalidas", exception.message)
        verify(userAuthenticator).validateUser(username, password)
        verify(tokenService, never()).generateToken(any())
    }

}

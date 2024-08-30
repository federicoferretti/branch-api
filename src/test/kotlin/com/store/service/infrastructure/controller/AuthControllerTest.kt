package com.store.service.infrastructure.controller

import com.store.service.application.usecase.LoginUserUseCase
import com.store.service.infrastructure.dto.LoginRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class AuthControllerTest {

    private val loginUserUseCase: LoginUserUseCase = mock()
    private lateinit var authController: AuthController

    @BeforeEach
    fun setUp() {
        authController = AuthController(loginUserUseCase)
    }

    @Test
    fun `login returns token for valid credentials`() {
        val loginRequest = LoginRequest("test User", "test Password")
        doReturn("validToken").`when`(loginUserUseCase).execute(any(), any())

        val result = authController.login(loginRequest)

        assertEquals("validToken", result)
    }
}

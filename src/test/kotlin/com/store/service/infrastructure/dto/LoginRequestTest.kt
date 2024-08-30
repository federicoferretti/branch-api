package com.store.service.infrastructure.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LoginRequestTest {

    @Test
    fun `LoginRequest is created with correct username and password`() {
        val loginRequest = LoginRequest("test User", "test Password")

        assertEquals("test User", loginRequest.username)
        assertEquals("test Password", loginRequest.password)
    }
}

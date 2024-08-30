package com.store.service.infrastructure.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ErrorResponseTest {

    @Test
    fun `ErrorResponse message is set correctly`() {
        val errorResponse = ErrorResponse(message = "Test message")

        assertEquals("Test message", errorResponse.message)
    }
}

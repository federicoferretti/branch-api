package com.store.service.infrastructure.exception

import com.store.service.domain.exception.AuthenticationFailedException
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.exception.BranchSaveException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

internal class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()
    private val request = mock(HttpRequest::class.java)

    @Test
    fun `should return NOT_FOUND status for BranchNotFoundException`() {
        val exception = BranchNotFoundException("Branch not found")

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.NOT_FOUND, response.status)
        assertEquals("Branch not found", response.body().message)
    }

    @Test
    fun `should return BAD_REQUEST status for BranchSaveException`() {
        val exception = BranchSaveException("Error saving branch")

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertEquals("Error saving branch", response.body().message)
    }

    @Test
    fun `should return BAD_REQUEST status for AuthenticationFailedException`() {
        val exception = AuthenticationFailedException("Authentication failed")

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertEquals("Authentication failed", response.body().message)
    }

    @Test
    fun `should return BAD_REQUEST status for ValidationException`() {
        val exception = ValidationException("Validation error")

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertEquals("Validation error", response.body().message)
    }

    @Test
    fun `should return INTERNAL_SERVER_ERROR status for other exceptions`() {
        val exception = RuntimeException("Unknown error")

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.status)
        assertEquals("Unknown error", response.body().message)
    }

    @Test
    fun `should return BAD_REQUEST status for ConstraintViolationException with violation messages`() {
        val constraintViolation = mock(ConstraintViolation::class.java)
        doReturn("Violation message").`when`(constraintViolation).message
        val exception = ConstraintViolationException(setOf(constraintViolation))

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.status)
        assertEquals("Violation message", response.body().message)
    }
}

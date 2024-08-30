package com.store.service.infrastructure.exception

import com.store.service.domain.exception.AuthenticationFailedException
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.exception.BranchSaveException
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import jakarta.validation.ConstraintViolationException
import jakarta.validation.ValidationException

@Singleton
@Produces
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler::class)
class GlobalExceptionHandler : ExceptionHandler<RuntimeException, HttpResponse<ErrorResponse>> {

    override fun handle(request: HttpRequest<*>, exception: RuntimeException): HttpResponse<ErrorResponse> {
        val status: HttpStatus
        val errorResponse: ErrorResponse

        when (exception) {
            is BranchNotFoundException -> {
                status = HttpStatus.NOT_FOUND
                errorResponse = ErrorResponse(message = exception.message)
            }

            is BranchSaveException -> {
                status = HttpStatus.BAD_REQUEST
                errorResponse = ErrorResponse(message = exception.message)
            }


            is AuthenticationFailedException -> {
                status = HttpStatus.BAD_REQUEST
                errorResponse = ErrorResponse(message = exception.message)
            }

            is ValidationException -> {
                status = HttpStatus.BAD_REQUEST
                errorResponse = handleValidationException(exception)
            }

            else -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR
                errorResponse = ErrorResponse(message = exception.message)
            }
        }

        return HttpResponse.status<ErrorResponse>(status).body(errorResponse)
    }

    private fun handleValidationException(exception: ValidationException): ErrorResponse {
        val messages = when (exception) {
            is ConstraintViolationException -> exception.constraintViolations.joinToString("; ") { violation ->
                violation.message
            }

            else -> exception.message ?: "Error de validación"
        }
        return ErrorResponse(
            message = messages
        )
    }
}

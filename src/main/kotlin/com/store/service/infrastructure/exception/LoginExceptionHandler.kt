package com.store.service.infrastructure.exception

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.security.authentication.AuthorizationException
import jakarta.inject.Singleton

@Singleton
@Produces
@Replaces(io.micronaut.security.authentication.DefaultAuthorizationExceptionHandler::class)
class LoginExceptionHandler : ExceptionHandler<AuthorizationException, HttpResponse<ErrorResponse>> {

    override fun handle(request: HttpRequest<*>, exception: AuthorizationException): HttpResponse<ErrorResponse> {
        val errorResponse = ErrorResponse(message = "No tienes permiso para acceder a este recurso.")
        return HttpResponse.status<ErrorResponse>(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

}
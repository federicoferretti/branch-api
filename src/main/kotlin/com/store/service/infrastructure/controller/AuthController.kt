package com.store.service.infrastructure.controller

import com.store.service.application.usecase.LoginUserUseCase
import com.store.service.infrastructure.dto.LoginRequest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.micronaut.validation.Validated
import jakarta.validation.Valid

@Validated
@Controller("/auth")
class AuthController(
    private val loginUserUseCase: LoginUserUseCase
) {

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun login(@Valid @Body loginRequest: LoginRequest): String {
        return loginUserUseCase.execute(loginRequest.username!!, loginRequest.password!!)
    }
}

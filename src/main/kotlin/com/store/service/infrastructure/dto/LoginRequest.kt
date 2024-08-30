package com.store.service.infrastructure.dto

import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotNull

@Serdeable.Deserializable
data class LoginRequest(
    @NotNull(message = "El usuario es un campo requerido")
    val username: String?,
    @NotNull(message = "La contraseña es un campo requerido")
    val password: String?
)

package com.store.service.infrastructure.exception

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorResponse(
    val message: String?
)

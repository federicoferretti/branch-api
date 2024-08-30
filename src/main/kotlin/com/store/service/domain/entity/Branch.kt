package com.store.service.domain.entity

import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal

@Serdeable.Serializable
data class Branch(

    val id: String? = null,
    val address: String,
    val longitude: BigDecimal,
    val latitude: BigDecimal
)

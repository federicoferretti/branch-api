package com.store.service.infrastructure.dto

import com.store.service.domain.entity.Branch
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.*

@Serdeable.Deserializable
data class SaveBranchRequest(

    @NotNull(message = "La dirección es un campo requerido")
    val address: String?,
    @NotNull(message = "La longitud es un campo requerido")
    @Min(value = -180, message = "La longitud debe estar entre 180 y -180 grados")
    @Max(value = 180, message = "La longitud debe estar entre 180 y -180 grados")
    val longitude: Double?,
    @NotNull(message = "La latitud es un campo requerido")
    @Min(value = (-90).toLong(), message = "La latitud debe estar entre 90 y -90  grados")
    @Max(value = 90, message = "La latitud debe estar entre 90 y -90 grados")
    val latitude: Double?
)

fun SaveBranchRequest.toBranch(): Branch {
    return Branch(
        address = this.address!!,
        longitude = String.format(Locale.US, "%.6f", this.longitude).toBigDecimal(),
        latitude = String.format(Locale.US, "%.6f", this.latitude).toBigDecimal()
    )
}

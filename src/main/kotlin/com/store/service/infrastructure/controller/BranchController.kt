package com.store.service.infrastructure.controller

import com.store.service.application.usecase.GetBranchByIdUseCase
import com.store.service.application.usecase.GetNearestBranchUseCase
import com.store.service.application.usecase.SaveBranchUseCase
import com.store.service.domain.entity.Branch
import com.store.service.infrastructure.dto.SaveBranchRequest
import com.store.service.infrastructure.dto.toBranch
import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.validation.Validated
import jakarta.validation.Valid
import jakarta.validation.constraints.*

@Validated
@Controller("/branch")
class BranchController(
    private val saveBranchUseCase: SaveBranchUseCase,
    private val getBranchByIdUseCase: GetBranchByIdUseCase,
    private val getNearestBranchUseCase: GetNearestBranchUseCase
) {

    @Post
    @Secured("ROLE_USER")
    @Status(HttpStatus.CREATED)
    fun save(@Valid @Body request: SaveBranchRequest): Branch {
        val branch = request.toBranch()
        return saveBranchUseCase.execute(branch)
    }

    @Get("/{id}")
    @Secured("ROLE_USER")
    fun getById(
        @Parameter @Size(min = 24, max = 24, message = "El ID debe tener exactamente 24 caracteres.")
        @Pattern(regexp = "[a-fA-F0-9]+", message = "El ID debe ser un hexString válido.")
        id: String
    ): Branch {
        return getBranchByIdUseCase.execute(id)
    }

    @Get("/nearest")
    @Secured("ROLE_USER")
    fun getNearest(
        @Parameter
        @NotNull(message = "La longitud es un campo requerido")
        @Min(value = -180, message = "La longitud debe estar entre 180 y -180 grados")
        @Max(value = 180, message = "La longitud debe estar entre 180 y -180 grados")
        longitude: Double?,
        @Parameter
        @NotNull(message = "La latitud es un campo requerido")
        @Min(value = -90, message = "La latitud debe estar entre 90 y -90 grados")
        @Max(value = 90, message = "La latitud debe estar entre 90 y -90 grados")
        latitude: Double?
    ): Branch {
        return getNearestBranchUseCase.execute(longitude!!, latitude!!)
    }
}

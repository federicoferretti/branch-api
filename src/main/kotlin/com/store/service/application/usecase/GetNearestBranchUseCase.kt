package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.repository.BranchRepository
import jakarta.inject.Singleton

@Singleton
class GetNearestBranchUseCase(
    private val branchRepository: BranchRepository
) {

    fun execute(longitude: Double, latitude: Double): Branch {
        return branchRepository.findNearestBranch(longitude, latitude)
            ?: throw BranchNotFoundException("No se encontro sucursal cercana")
    }
}
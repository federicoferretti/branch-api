package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchSaveException
import com.store.service.domain.repository.BranchRepository
import com.store.service.domain.service.CacheInterface
import jakarta.inject.Singleton

@Singleton
class SaveBranchUseCase(
    private val branchRepository: BranchRepository,
    private val cacheInterface: CacheInterface
) {

    fun execute(branch: Branch): Branch {
        val branchSaved =
            branchRepository.save(branch) ?: throw BranchSaveException("Error guardando la nueva sucursal")

        cacheInterface.saveBranch(branchSaved)

        return branchSaved
    }
}
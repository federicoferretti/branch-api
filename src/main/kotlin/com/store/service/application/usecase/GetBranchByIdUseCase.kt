package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.repository.BranchRepository
import com.store.service.domain.service.CacheInterface
import jakarta.inject.Singleton

@Singleton
class GetBranchByIdUseCase(
    private val branchRepository: BranchRepository,
    private val cacheInterface: CacheInterface
) {
    fun execute(id: String): Branch {
        val cachedBranch = cacheInterface.findBranchById(id)

        if (cachedBranch != null) {
            return cachedBranch
        }

        val branch = branchRepository.findById(id) ?: throw BranchNotFoundException("La sucursal $id no fue encontrada")

        cacheInterface.saveBranch(branch)
        return branch
    }
}
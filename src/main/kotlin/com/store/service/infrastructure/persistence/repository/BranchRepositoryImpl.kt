package com.store.service.infrastructure.persistence.repository

import com.store.service.application.mapper.BranchMapper
import com.store.service.domain.entity.Branch
import com.store.service.domain.repository.BranchRepository
import io.micronaut.data.annotation.Repository

@Repository
open class BranchRepositoryImpl(
    private val branchRepositoryCrud: BranchRepositoryCrud
) : BranchRepository {
    override fun save(branch: Branch): Branch? {
        val branchDAO = BranchMapper.toDAO(branch)
        val branchSaved = branchRepositoryCrud.save(branchDAO)
        return BranchMapper.toDomain(branchSaved)
    }

    override fun findById(id: String): Branch? {
        val branch = branchRepositoryCrud.findById(id).orElse(null)
        return BranchMapper.toDomain(branch)
    }

    override fun findNearestBranch(longitude: Double, latitude: Double): Branch? {
        val branch = branchRepositoryCrud.findNearestBranch(latitude, longitude)
            .orElse(null)
        return BranchMapper.toDomain(branch)
    }
}


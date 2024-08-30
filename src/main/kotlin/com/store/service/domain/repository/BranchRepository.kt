package com.store.service.domain.repository

import com.store.service.domain.entity.Branch


interface BranchRepository {

    fun save(branch: Branch): Branch?

    fun findNearestBranch(longitude: Double, latitude: Double): Branch?

    fun findById(id: String): Branch?
}

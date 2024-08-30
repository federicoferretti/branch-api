package com.store.service.domain.service

import com.store.service.domain.entity.Branch

interface CacheInterface {

    fun saveBranch(branchSaved: Branch)

    fun findBranchById(id: String): Branch?
}

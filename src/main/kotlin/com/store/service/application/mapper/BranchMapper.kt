package com.store.service.application.mapper

import com.store.service.domain.entity.Branch
import com.store.service.infrastructure.persistence.dao.BranchDAO
import com.store.service.infrastructure.persistence.dao.LocationDAO

object BranchMapper {

    fun toDAO(branch: Branch): BranchDAO {
        return BranchDAO(
            id = branch.id,
            address = branch.address,
            location = LocationDAO(branch.longitude, branch.latitude)
        )
    }

    fun toDomain(branchDAO: BranchDAO?): Branch? {
        return branchDAO?.let {
            Branch(
                id = it.id,
                address = it.address,
                longitude = it.location.coordinates[0],
                latitude = it.location.coordinates[1]
            )
        }
    }

}

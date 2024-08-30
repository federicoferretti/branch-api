package com.store.service.infrastructure.persistence.repository

import com.store.service.infrastructure.persistence.dao.BranchDAO
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@MongoRepository
interface BranchRepositoryCrud : CrudRepository<BranchDAO, String> {

    @MongoFindQuery(
        "{'location': {'\$near': {'\$geometry': {'type': 'Point', 'coordinates': [:longitude, :latitude]}}}}"
    )
    fun findNearestBranch(latitude: Double, longitude: Double): Optional<BranchDAO>
}
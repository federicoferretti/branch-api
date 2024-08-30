package com.store.service.infrastructure.persistence.dao

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import org.bson.codecs.pojo.annotations.BsonDiscriminator
import org.bson.codecs.pojo.annotations.BsonProperty

@Serdeable
@MappedEntity(value = "branch")
@BsonDiscriminator
data class BranchDAO(

    @field:Id
    @field:GeneratedValue
    @BsonProperty("id")
    val id: String? = null,
    @BsonProperty("address")
    val address: String,
    @BsonProperty("location")
    val location: LocationDAO
)

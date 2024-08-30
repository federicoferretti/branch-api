package com.store.service.infrastructure.persistence.dao

import io.micronaut.serde.annotation.Serdeable
import org.bson.codecs.pojo.annotations.BsonDiscriminator
import org.bson.codecs.pojo.annotations.BsonProperty
import java.math.BigDecimal

@Serdeable
@BsonDiscriminator
data class LocationDAO(

    @BsonProperty("type")
    val type: String = "Point",
    @BsonProperty("coordinates")
    val coordinates: List<BigDecimal>
) {
    constructor(longitude: BigDecimal, latitude: BigDecimal) : this(
        type = "Point",
        coordinates = listOf(longitude, latitude)
    )
}

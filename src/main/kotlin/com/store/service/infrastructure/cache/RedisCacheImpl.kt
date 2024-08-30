package com.store.service.infrastructure.cache

import com.store.service.domain.entity.Branch
import com.store.service.domain.service.CacheInterface
import io.lettuce.core.RedisClient
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode

@Singleton
class RedisCacheImpl(
    private val redisClient: RedisClient
) : CacheInterface {

    private val logger = LoggerFactory.getLogger(RedisCacheImpl::class.java)

    override fun saveBranch(branchSaved: Branch) {
        try {
            val redisCommands = redisClient.connect().sync()
            val exists: Long = redisCommands.exists("branch:${branchSaved.id}")

            if (exists == 0L) {
                redisCommands.hset(
                    "branch:${branchSaved.id}", mapOf(
                        "address" to branchSaved.address,
                        "longitude" to branchSaved.longitude.toString(),
                        "latitude" to branchSaved.latitude.toString()
                    )
                )
            }
        } catch (e: Exception) {
            logger.error("Error guardando la sucursal ${branchSaved.id} en redis")
        }
    }

    override fun findBranchById(id: String): Branch? {
        try {
            val redisCommands = redisClient.connect().sync()
            val branchData = redisCommands.hmget("branch:$id", "address", "longitude", "latitude")

            if (branchData.isNullOrEmpty() || branchData.any { it.isEmpty }) {
                return null
            }

            val address = branchData[0].value
            val longitude = convertToBigDecimalWithSixDecimalPlaces(branchData[1].value)
            val latitude = convertToBigDecimalWithSixDecimalPlaces(branchData[2].value)

            return Branch(
                id = id,
                address = address,
                longitude = longitude,
                latitude = latitude,
            )
        } catch (e: Exception) {
            logger.error("Error buscando la sucursal $id en redis")
            return null
        }
    }

    private fun convertToBigDecimalWithSixDecimalPlaces(value: String): BigDecimal {
        return value.toBigDecimal().setScale(6, RoundingMode.HALF_UP)
    }
}
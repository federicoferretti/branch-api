package com.store.service.infrastructure.cache

import com.store.service.domain.entity.Branch
import io.lettuce.core.KeyValue
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import java.math.BigDecimal

class RedisCacheImplTest {

    private lateinit var redisCacheImpl: RedisCacheImpl
    private val redisClient: RedisClient = mock()
    private val statefulConnection: StatefulRedisConnection<String, String> = mock()
    private val redisCommands: RedisCommands<String, String> = mock()

    private lateinit var branch: Branch

    @BeforeEach
    fun setUp() {
        `when`(redisClient.connect()).thenReturn(statefulConnection)
        `when`(redisClient.connect().sync()).thenReturn(redisCommands)

        redisCacheImpl = RedisCacheImpl(redisClient)
        branch = Branch(
            id = "1",
            address = "Test Address",
            longitude = BigDecimal("10.20"),
            latitude = BigDecimal("20.65")
        )
    }

    @Test
    fun `should save branch successfully when branch does not exist`() {
        `when`(redisCommands.exists(anyString())).thenReturn(0L)

        redisCacheImpl.saveBranch(branch)

        verify(redisCommands).hset(
            "branch:1", mapOf(
                "address" to "Test Address",
                "longitude" to "10.20",
                "latitude" to "20.65"
            )
        )
    }

    @Test
    fun `should not save branch if it already exists`() {
        `when`(redisCommands.exists(anyString())).thenReturn(1L)

        redisCacheImpl.saveBranch(branch)

        verify(redisCommands, never()).hset(anyString(), anyMap())
    }

    @Test
    fun `should log error if saving branch fails`() {
        doThrow(RuntimeException("Connection failed")).`when`(redisCommands).exists(anyString())

        redisCacheImpl.saveBranch(branch)

        verify(redisCommands).exists("branch:1")
    }

    @Test
    fun `should return branch when found in Redis`() {
        val mockedResult = listOf(
            mockKeyValue("address", "Test Address"),
            mockKeyValue("longitude", "10.20"),
            mockKeyValue("latitude", "20.65")
        )

        `when`(redisCommands.hmget("branch:1", "address", "longitude", "latitude"))
            .thenReturn(mockedResult)

        val result = redisCacheImpl.findBranchById("1")

        assert(result != null)
        assert(result?.id == "1")
        assert(result?.address == "Test Address")
        assert(result?.longitude == BigDecimal("10.200000"))
        assert(result?.latitude == BigDecimal("20.650000"))
    }


    @Test
    fun `should return null when branch is not found in Redis`() {
        `when`(redisCommands.hmget(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(emptyList())

        val result = redisCacheImpl.findBranchById("1")

        assert(result == null)
    }

    @Test
    fun `should log error and return null if find branch fails`() {
        doThrow(RuntimeException("Connection failed")).`when`(redisCommands)
            .hmget(anyString(), anyString(), anyString(), anyString())

        val result = redisCacheImpl.findBranchById("1")

        assert(result == null)
    }

    private fun mockKeyValue(key: String, value: String): KeyValue<String, String> {
        val mockKeyValue = mock(KeyValue::class.java) as KeyValue<String, String>
        `when`(mockKeyValue.key).thenReturn(key)
        `when`(mockKeyValue.value).thenReturn(value)
        return mockKeyValue
    }
}

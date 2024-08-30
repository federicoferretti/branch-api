package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.repository.BranchRepository
import com.store.service.domain.service.CacheInterface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*
import java.math.BigDecimal

class GetBranchByIdUseCaseTest {
    private val branchRepository: BranchRepository = mock()
    private val cacheInterface: CacheInterface = mock()
    private lateinit var getBranchByIdUseCase: GetBranchByIdUseCase

    private val searchedId = "1"
    private lateinit var branch: Branch

    @BeforeEach
    fun setUp() {
        getBranchByIdUseCase = GetBranchByIdUseCase(branchRepository, cacheInterface)
        branch = Branch(
            id = "1",
            address = "test address",
            longitude = BigDecimal("10.5"),
            latitude = BigDecimal("20.5")
        )
    }

    @Test
    fun `should return branch from cache if present`() {
        doReturn(branch).whenever(cacheInterface).findBranchById(searchedId)

        val result = getBranchByIdUseCase.execute(searchedId)

        assertNotNull(result)
        assertEquals(branch, result)
        assertEquals(searchedId, result.id)
        verify(cacheInterface).findBranchById(searchedId)
        verify(branchRepository, never()).findById(anyString())
        verify(cacheInterface, never()).saveBranch(any())
    }

    @Test
    fun `should return branch from repository and cache it if not present in cache`() {
        doReturn(null).whenever(cacheInterface).findBranchById(searchedId)
        doReturn(branch).whenever(branchRepository).findById(searchedId)

        val result = getBranchByIdUseCase.execute(searchedId)

        assertNotNull(result)
        assertEquals(branch, result)
        assertEquals(searchedId, result.id)
        verify(cacheInterface).findBranchById(searchedId)
        verify(branchRepository).findById(searchedId)
        verify(cacheInterface).saveBranch(branch)
    }

    @Test
    fun `should throw BranchNotFoundException if branch is not found in cache or repository`() {
        doReturn(null).whenever(cacheInterface).findBranchById(searchedId)
        doReturn(null).whenever(branchRepository).findById(searchedId)

        val exception = assertThrows<BranchNotFoundException> {
            getBranchByIdUseCase.execute(searchedId)
        }

        assertEquals(BranchNotFoundException::class, exception::class)
        assertEquals("La sucursal $searchedId no fue encontrada", exception.message)
        verify(cacheInterface).findBranchById(searchedId)
        verify(branchRepository).findById(searchedId)
        verify(cacheInterface, never()).saveBranch(any())
    }
}

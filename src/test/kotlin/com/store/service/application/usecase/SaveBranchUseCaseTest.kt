package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchSaveException
import com.store.service.domain.repository.BranchRepository
import com.store.service.domain.service.CacheInterface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.math.BigDecimal

class SaveBranchUseCaseTest {
    private val branchRepository: BranchRepository = mock()
    private val cacheInterface: CacheInterface = mock()
    private lateinit var saveBranchUseCase: SaveBranchUseCase

    private lateinit var branch: Branch

    @BeforeEach
    fun setUp() {
        saveBranchUseCase = SaveBranchUseCase(branchRepository, cacheInterface)
        branch = Branch(
            id = "1",
            address = "test address",
            longitude = BigDecimal("10.5"),
            latitude = BigDecimal("20.5")
        )
    }

    @Test
    fun `execute should save branch successfully`() {
        doReturn(branch).`when`(branchRepository).save(any())

        val result = saveBranchUseCase.execute(branch)

        assertNotNull(result)
        assertEquals(branch, result)
        verify(branchRepository).save(branch)
        verify(cacheInterface).saveBranch(branch)
    }

    @Test
    fun `execute should throw BranchSaveException when save fails`() {
        doReturn(null).`when`(branchRepository).save(any())

        val exception = assertThrows<BranchSaveException> {
            saveBranchUseCase.execute(branch)
        }

        assertEquals("Error guardando la nueva sucursal", exception.message)
        verify(branchRepository).save(branch)
    }
}

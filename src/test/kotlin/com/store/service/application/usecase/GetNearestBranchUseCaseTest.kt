package com.store.service.application.usecase

import com.store.service.domain.entity.Branch
import com.store.service.domain.exception.BranchNotFoundException
import com.store.service.domain.repository.BranchRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import java.math.BigDecimal

class GetNearestBranchUseCaseTest {
    private val branchRepository: BranchRepository = mock()
    private lateinit var getNearestBranchUseCase: GetNearestBranchUseCase

    private val LONGITUDE = 13.5
    private val LATITUDE = 11.55

    @BeforeEach
    fun setUp() {
        getNearestBranchUseCase = GetNearestBranchUseCase(branchRepository)
    }

    @Test
    fun `should return nearest branch when found`() {
        val expectedBranch = Branch(
            id = "1",
            address = "test address",
            longitude = BigDecimal(LONGITUDE.toString()),
            latitude = BigDecimal(LATITUDE.toString())
        )

        doReturn(expectedBranch).`when`(branchRepository).findNearestBranch(anyDouble(), anyDouble())

        val result = getNearestBranchUseCase.execute(LONGITUDE, LATITUDE)

        assertNotNull(result)
        assertEquals(expectedBranch, result)
        verify(branchRepository).findNearestBranch(LONGITUDE, LATITUDE)
    }

    @Test
    fun `should throw BranchNotFoundException when no branch is found`() {
        doReturn(null).`when`(branchRepository).findNearestBranch(anyDouble(), anyDouble())

        val exception = assertThrows<BranchNotFoundException> {
            getNearestBranchUseCase.execute(LONGITUDE, LATITUDE)
        }

        verify(branchRepository).findNearestBranch(LONGITUDE, LATITUDE)
        assertEquals(BranchNotFoundException::class, exception::class)
        assertEquals("No se encontro sucursal cercana", exception.message)
    }
}

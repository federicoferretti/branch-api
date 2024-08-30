package com.store.service.infrastructure.controller

import com.store.service.application.usecase.GetBranchByIdUseCase
import com.store.service.application.usecase.GetNearestBranchUseCase
import com.store.service.application.usecase.SaveBranchUseCase
import com.store.service.domain.entity.Branch
import com.store.service.infrastructure.dto.SaveBranchRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.math.BigDecimal

internal class BranchControllerTest {

    private lateinit var saveBranchUseCase: SaveBranchUseCase
    private lateinit var getBranchByIdUseCase: GetBranchByIdUseCase
    private lateinit var getNearestBranchUseCase: GetNearestBranchUseCase
    private lateinit var branchController: BranchController

    private lateinit var branch: Branch

    @BeforeEach
    fun setUp() {
        saveBranchUseCase = mock()
        getBranchByIdUseCase = mock()
        getNearestBranchUseCase = mock()
        branchController = BranchController(saveBranchUseCase, getBranchByIdUseCase, getNearestBranchUseCase)

        branch = Branch("1", "Test Address", BigDecimal("10.80"), BigDecimal("70.5"))
    }

    @Test
    fun `save returns created branch`() {
        val request = SaveBranchRequest("Test Address", 22.50, 14.30)
        doReturn(branch).`when`(saveBranchUseCase).execute(any())

        val result = branchController.save(request)

        assertEquals(branch, result)
    }

    @Test
    fun `getById returns branch by id`() {
        doReturn(branch).`when`(getBranchByIdUseCase).execute(any())

        val result = branchController.getById("1")

        assertEquals(branch, result)
    }

    @Test
    fun `getNearest returns nearest branch`() {
        doReturn(branch).`when`(getNearestBranchUseCase).execute(any(), any())

        val result = branchController.getNearest(23.344, 14.334)

        assertEquals(branch, result)
    }
}

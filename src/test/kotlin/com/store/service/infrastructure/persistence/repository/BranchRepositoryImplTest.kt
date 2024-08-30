package com.store.service.infrastructure.persistence.repository

import com.store.service.domain.entity.Branch
import com.store.service.infrastructure.persistence.dao.BranchDAO
import com.store.service.infrastructure.persistence.dao.LocationDAO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.math.BigDecimal
import java.util.*

internal class BranchRepositoryImplTest {
    private val branchRepositoryCrud: BranchRepositoryCrud = mock()
    private lateinit var branchRepositoryImpl: BranchRepositoryImpl

    private lateinit var branch: Branch
    private lateinit var locationDAO: LocationDAO
    private lateinit var branchDAO: BranchDAO

    @BeforeEach
    fun setUp() {
        branchRepositoryImpl = BranchRepositoryImpl(branchRepositoryCrud)

        branch = Branch("id", "test address", BigDecimal("11.5"), BigDecimal("14.2"))
        locationDAO = LocationDAO(BigDecimal("11.5"), BigDecimal("14.2"))
        branchDAO = BranchDAO("id", "test address", locationDAO)
    }

    @Test
    fun `save branch successfully`() {
        doReturn(branchDAO).`when`(branchRepositoryCrud).save(any())

        val result = branchRepositoryImpl.save(branch)

        assertEquals(branchDAO.id, result?.id)
        assertEquals(branchDAO.location.coordinates[0], result?.longitude)
        assertEquals(branchDAO.location.coordinates[1], result?.latitude)
        assertEquals(branchDAO.address, result?.address)
    }

    @Test
    fun `findById returns branch when it exists`() {
        doReturn(Optional.of(branchDAO)).`when`(branchRepositoryCrud).findById(any())

        val result = branchRepositoryImpl.findById("1")

        assertEquals(branch, result)
    }

    @Test
    fun `findById returns null when branch does not exist`() {
        doReturn(Optional.empty<BranchDAO>()).`when`(branchRepositoryCrud).findById(any())

        val result = branchRepositoryImpl.findById("id")

        assertNull(result)
    }

    @Test
    fun `findNearestBranch returns nearest branch when it exists`() {
        doReturn(Optional.of(branchDAO)).`when`(branchRepositoryCrud).findNearestBranch(any(), any())

        val result = branchRepositoryImpl.findNearestBranch(0.0, 0.0)

        assertEquals(branch, result)
    }

    @Test
    fun `findNearestBranch returns null when no branch is near`() {
        doReturn(Optional.empty<BranchDAO>()).`when`(branchRepositoryCrud).findNearestBranch(any(), any())

        val result = branchRepositoryImpl.findNearestBranch(0.0, 0.0)

        assertNull(result)
    }
}

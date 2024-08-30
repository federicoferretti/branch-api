package com.store.service.application.mapper

import com.store.service.domain.entity.Branch
import com.store.service.infrastructure.persistence.dao.BranchDAO
import com.store.service.infrastructure.persistence.dao.LocationDAO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class BranchMapperTest {

    @Test
    fun `should map to DAO when given valid branch`() {
        val branch = Branch("1", "address", BigDecimal("1.123456"), BigDecimal("2.123456"))
        val branchDAO = BranchMapper.toDAO(branch)

        assertEquals(branch.id, branchDAO.id)
        assertEquals(branch.address, branchDAO.address)
        assertEquals(branch.longitude, branchDAO.location.coordinates[0])
        assertEquals(branch.latitude, branchDAO.location.coordinates[1])
    }

    @Test
    fun `should map to domain when given valid DAO`() {
        val branchDAO = BranchDAO("1", "address", LocationDAO(BigDecimal("1.123456"), BigDecimal("2.123456")))
        val branch = BranchMapper.toDomain(branchDAO)

        assertEquals(branchDAO.id, branch?.id)
        assertEquals(branchDAO.address, branch?.address)
        assertEquals(branchDAO.location.coordinates[0], branch?.longitude)
        assertEquals(branchDAO.location.coordinates[1], branch?.latitude)
    }

    @Test
    fun `should return null when given null DAO`() {
        val branch = BranchMapper.toDomain(null)
        assertNull(branch)
    }
}

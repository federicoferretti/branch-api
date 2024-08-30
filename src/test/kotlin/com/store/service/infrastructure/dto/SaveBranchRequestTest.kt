package com.store.service.infrastructure.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class SaveBranchRequestTest {

    @Test
    fun `should convert SaveBranchRequest to Branch with more than 6 decimal digits`() {
        val request = SaveBranchRequest("Test Address", -179.999999999, -89.999999999)
        val branch = request.toBranch()

        assertEquals("Test Address", branch.address)
        assertEquals(BigDecimal("-180.000000"), branch.longitude)
        assertEquals(BigDecimal("-90.000000"), branch.latitude)
    }

    @Test
    fun `convert SaveBranchRequest to Branch with  6 decimal digits`() {
        val request = SaveBranchRequest("Test Address", 179.999999, 89.999999)
        val branch = request.toBranch()

        assertEquals("Test Address", branch.address)
        assertEquals(BigDecimal("179.999999"), branch.longitude)
        assertEquals(BigDecimal("89.999999"), branch.latitude)
    }

    @Test
    fun `convert SaveBranchRequest to Branch with less than 6 decimal digits`() {
        val request = SaveBranchRequest("Test Address", -179.9, -89.9)
        val branch = request.toBranch()

        assertEquals("Test Address", branch.address)
        assertEquals(BigDecimal("-179.900000"), branch.longitude)
        assertEquals(BigDecimal("-89.900000"), branch.latitude)
    }
}

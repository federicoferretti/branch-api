package com.store.service.infrastructure.security

import com.mongodb.assertions.Assertions.assertFalse
import com.mongodb.assertions.Assertions.assertTrue
import com.store.service.infrastructure.persistence.dao.UserDAO
import com.store.service.infrastructure.persistence.repository.UserRepositoryCrud
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal class UserAuthenticatorImplTest {

    private val userRepositoryCrud: UserRepositoryCrud = mock()
    private lateinit var userAuthenticatorImpl: UserAuthenticatorImpl

    private lateinit var userDAO: UserDAO

    @BeforeEach
    fun setUp() {
        userAuthenticatorImpl = UserAuthenticatorImpl(userRepositoryCrud)
        userDAO = UserDAO("id", "testUser", "testPassword", listOf("ROLE"))
    }

    @Test
    fun `validateUser returns success when user exists and password matches`() {
        val username = "testUser"
        doReturn(userDAO).`when`(userRepositoryCrud).findByUsername(username)

        val result = userAuthenticatorImpl.validateUser(username, "testPassword")

        assertTrue(result.isAuthenticated)
        assertEquals(username, result.authentication.get().name)
    }

    @Test
    fun `validateUser returns failure when user does not exist`() {
        val username = "testUser"
        doReturn(null).`when`(userRepositoryCrud).findByUsername(username)

        val result = userAuthenticatorImpl.validateUser(username, "testPassword")

        assertFalse(result.isAuthenticated)
    }

    @Test
    fun `validateUser returns failure when password does not match`() {
        val username = "testUser"
        doReturn(userDAO).`when`(userRepositoryCrud).findByUsername(username)

        val result = userAuthenticatorImpl.validateUser(username, "otherPassword")

        assertFalse(result.isAuthenticated)
    }
}

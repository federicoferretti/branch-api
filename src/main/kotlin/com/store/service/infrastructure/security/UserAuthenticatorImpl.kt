package com.store.service.infrastructure.security

import com.store.service.domain.service.UserAuthenticator
import com.store.service.infrastructure.persistence.repository.UserRepositoryCrud
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.authentication.provider.AuthenticationProvider
import jakarta.inject.Singleton

@Singleton
class UserAuthenticatorImpl(
    private val userRepositoryCrud: UserRepositoryCrud
) : UserAuthenticator, AuthenticationProvider<HttpRequest<*>, String, String> {

    override fun validateUser(username: String, password: String): AuthenticationResponse {
        val authenticationRequest = UsernamePasswordCredentials(username, password)
        return authenticate(null, authenticationRequest)
    }

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<String, String>
    ): AuthenticationResponse {
        val username = authenticationRequest.identity
        val password = authenticationRequest.secret

        val user = userRepositoryCrud.findByUsername(username)
        return if (user != null && user.password == password) {
            AuthenticationResponse.success(username, listOf("ROLE_USER"))
        } else {
            AuthenticationResponse.failure()
        }
    }
}

import com.store.service.infrastructure.exception.LoginExceptionHandler
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.AuthorizationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

internal class LoginExceptionHandlerTest {

    private val handler = LoginExceptionHandler()
    private val request = mock(HttpRequest::class.java)
    private val authentication = mock(Authentication::class.java)

    @Test
    fun `should return UNAUTHORIZED status for AuthorizationException`() {
        val exception = AuthorizationException(authentication)

        val response = handler.handle(request, exception)

        assertEquals(HttpStatus.UNAUTHORIZED, response.status)
        assertEquals("No tienes permiso para acceder a este recurso.", response.body()?.message)
    }
}

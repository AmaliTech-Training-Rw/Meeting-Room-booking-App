import com.amalitech.domain.authentication.R
import com.example.authentication.use_case.ValidateEmail
import com.example.authentication.util.UiText
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateEmailTest {
    @MockK
    private lateinit var validateEmail: ValidateEmail

    @Before
    fun setup() {
        validateEmail = spyk(ValidateEmail())
    }

    @Test
    fun `validate email with valid email returns null`() {
        // GIVEN - A valid email address and a mockk behavior for isEmailValid
        val email = "test@example.com"
        every { validateEmail.isEmailValid(email) } returns true

        // WHEN - validateEmail is called
        val result = validateEmail(email)

        // THEN - the result is null
        assertEquals(null, result)
    }

    @Test
    fun `validate email with invalid email returns UiText`() {
        // GIVEN - An invalid email address and a mockk behavior for isEmailValid
        val email = "invalid_email"
        every { validateEmail.isEmailValid(email) } returns false

        // WHEN - validateEmail is called
        val result = validateEmail(email)

        // THEN - the result is a UiText instance
        val expectedError = UiText.StringResource(R.string.error_email_not_valid)
        assertEquals(expectedError, result)
    }

    @Test
    fun `validate email with empty email address returns UiText`() {
        // GIVEN - A blank email address and a mockk behavior for isEmailValid
        val email = ""
        every { validateEmail.isEmailValid(email) } returns true

        // WHEN - validateEmail is called
        val result = validateEmail(email)

        // THEN - the result is a UiText instance
        val expectedError = UiText.StringResource(R.string.error_email_not_valid)
        assertEquals(expectedError, result)
    }

}

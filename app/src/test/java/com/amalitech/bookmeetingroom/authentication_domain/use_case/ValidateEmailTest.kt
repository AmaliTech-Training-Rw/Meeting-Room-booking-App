import com.amalitech.bookmeetingroom.util.UiText
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.authentication_domain.use_case.ValidateEmail
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
    fun validateEmail_validEmail_returnsNull() {
        // GIVEN - A valid email address and a mockk behavior for isEmailValid
        val email = "test@example.com"
        every { validateEmail.isEmailValid(email) } returns true

        // WHEN - validateEmail is called
        val result = validateEmail(email)

        // THEN - the result is null
        assertEquals(null, result)
    }

    @Test
    fun validateEmail_invalidEmail_returnsUiText() {
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
    fun validateEmail_emptyEmail_returnsUiText() {
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

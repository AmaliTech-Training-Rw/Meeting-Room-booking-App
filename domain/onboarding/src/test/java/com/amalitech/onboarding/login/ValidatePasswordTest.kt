package com.amalitech.onboarding.login

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.login.use_case.ValidatePassword
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatePasswordTest {

    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `validate password with blank password returns UiText`() {
        // GIVEN - blank password
        val password = ""

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `validate password with less than 7 characters returns UiText`() {
        // GIVEN - less than 7 characters password
        val password = "ndojv"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `validate password with no special characters returns UiText`() {
        // GIVEN - password without special characters
        val password = "Mdjnkvvrvo14"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `validate password without number characters returns UiText`() {
        // GIVEN - password without numbers
        val password = "Mdjnkvvrvo$"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `validate password without uppercase characters returns UiText`() {
        // GIVEN - password without  uppercase character
        val password = "djnkvvrvo$11"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }
    @Test
    fun `validate password without lowercase characters returns UiText`() {
        // GIVEN - password without lowercase characters
        val password = "DKBTROT$11"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }


    @Test
    fun `validate password with valid password returns null`() {
        // GIVEN - a valid password
        val password = "Mjuofud@\$VR44"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is null
        assertEquals(null, result)
    }
}

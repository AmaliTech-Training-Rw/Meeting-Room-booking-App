package com.example.oboarding_domain.login

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.example.oboarding_domain.login.use_case.ValidatePassword
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
    fun `validate password with valid password returns null`() {
        // GIVEN - a password not blank
        val password = "this is not blank"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is null
        assertEquals(null, result)
    }
}

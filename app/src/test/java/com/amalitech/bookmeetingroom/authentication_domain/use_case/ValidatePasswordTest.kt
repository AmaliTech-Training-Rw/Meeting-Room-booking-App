package com.amalitech.bookmeetingroom.login_domain.use_case

import com.amalitech.bookmeetingroom.util.UiText
import com.amalitech.bookmeetingroom.R
import org.junit.Assert.*

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatePasswordTest {

    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    fun validatePassword_blankPassword_returnsUiText() {
        // GIVEN - blank password
        val password = ""

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is a UiText instance
        val expectedResult = UiText.StringResource(R.string.error_password_is_blank)
        assertEquals(expectedResult, result)
    }


    fun validatePassword_notBlankPassword_returnsNull() {
        // GIVEN - a password not blank
        val password = "this is not blank"

        // WHEN - validate password is called
        val result = validatePassword(password)

        // THEN - the result is null
        assertEquals(null, result)
    }


}
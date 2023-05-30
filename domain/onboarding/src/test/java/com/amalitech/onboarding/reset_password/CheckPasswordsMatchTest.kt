package com.amalitech.onboarding.reset_password

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckPasswordsMatchTest {
    private val checkPasswordsMatch = CheckPasswordsMatch()

    @Test
    fun checkPasswordMatch_samePasswords_returnNull() {
        // GIVEN - Two same passwords
        val newPassword = "password"
        val newPasswordConfirmation = "password"

        // WHEN - Check passwordsMatch is called with the given passwords
        val result = checkPasswordsMatch(newPassword, newPasswordConfirmation)

        // THEN - The result is null
        assertEquals(null, result)
    }

    @Test
    fun checkPasswordMatch_emptyStrings_returnUiText() {
        // GIVEN - Two same passwords but blank
        val newPassword = ""
        val newPasswordConfirmation = ""

        // WHEN - Check passwordsMatch is called with the given passwords
        val result = checkPasswordsMatch(newPassword, newPasswordConfirmation)

        // THEN - The result is an instance of UiText
        assertEquals(UiText.StringResource(R.string.error_passwords_dont_match), result)
    }


    @Test
    fun checkPasswordMatch_differentPasswords_returnUiText() {
        // GIVEN - Two different passwords
        val newPassword = "password"
        val newPasswordConfirmation = "confirmation"

        // WHEN - Check passwordsMatch is called with the given passwords
        val result = checkPasswordsMatch(newPassword, newPasswordConfirmation)

        // THEN - The result is an instance of UiText
        assertEquals(UiText.StringResource(R.string.error_passwords_dont_match), result)
    }
}

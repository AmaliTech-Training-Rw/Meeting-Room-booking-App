package com.amalitech.onboarding.signup.use_case


import com.amalitech.core.R
import com.amalitech.core.util.UiText
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test

class CheckValuesNotBlankTest {

    private lateinit var checkValuesNotBlank: CheckValuesNotBlank

    @Before
    fun setUp() {
        checkValuesNotBlank = CheckValuesNotBlank()
    }

    @Test
    fun `Ensures that an error is returned when any blank values is provided`() {
        val blank = ""
        val one = "one"
        val two = "two"
        val three = "three"

        val result = checkValuesNotBlank(blank, one, two, three)

        assertEquals(UiText.StringResource(R.string.error_value_is_blank), result)
    }

    @Test
    fun `Ensures that no error is returned when all provided values aren't blank`() {
        val blank = "yes"
        val one = "one"
        val two = "two"
        val three = "three"

        val result = checkValuesNotBlank(blank, one, two, three)

        assertEquals(null, result)
    }
}

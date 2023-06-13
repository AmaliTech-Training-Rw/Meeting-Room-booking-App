package com.amalitech.core_ui.util

import com.amalitech.core.util.UiText
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthenticationBaseViewModelTest {

    private lateinit var viewModel: AuthenticationBaseViewModel<String>

    @Before
    fun setUp() {
        viewModel = AuthenticationBaseViewModel()
    }

    @Test
    fun `ensures value of snackbar is well reset`() {
        viewModel.setSnackBarValue(UiText.DynamicString("testing"))

        viewModel.onSnackBarShown()

        assertEquals(null, (viewModel.publicBaseResult.value as UiState.Error).error)
    }
}
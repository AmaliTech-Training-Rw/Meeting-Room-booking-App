package com.amalitech.core_ui.util

import com.amalitech.core.util.UiText
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BaseViewModelTest {

    private lateinit var viewModel: BaseViewModel<String>

    @Before
    fun setUp() {
        viewModel = BaseViewModel()
    }

    @Test
    fun `ensures value of snackbar is well reset`() {
        viewModel.setSnackBarValue(UiText.DynamicString("testing"))

        viewModel.onSnackBarShown()

        assertEquals(null, (viewModel.uiStateFlow.value as UiState.Error).error)
    }
}
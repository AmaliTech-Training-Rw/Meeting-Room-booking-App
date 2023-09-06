package com.amalitech.core_ui.util

import androidx.lifecycle.ViewModel
import com.amalitech.core.util.UiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel<T> : ViewModel() {
    protected val _uiStateFlow: MutableStateFlow<UiState<T>?> =
        MutableStateFlow(null)
    val uiStateFlow = _uiStateFlow.asStateFlow()
    protected var job: Job? = null

    /**
     * onSnackBarShown - Reset snackBarValue to null
     */
    fun onSnackBarShown(
        functionToExecute: (() -> Unit)? = null
    ) {
        _uiStateFlow.update { state ->
            (state as UiState.Error<T>).copy(
                error = null
            )
        }
        if (functionToExecute != null)
            functionToExecute()
    }

    internal fun setSnackBarValue(value: UiText?) {
        _uiStateFlow.update {
            UiState.Error(
                error = value
            )
        }
    }
}
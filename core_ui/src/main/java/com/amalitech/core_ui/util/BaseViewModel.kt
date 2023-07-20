package com.amalitech.core_ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<T> : ViewModel() {
    protected val _uiStateFlow: MutableStateFlow<UiState<T>?> =
        MutableStateFlow(null)
    val uiStateFlow = _uiStateFlow.asStateFlow()
    protected var job: Job? = null

    /**
     * onSnackBarShown - Reset snackBarValue to null
     */
    fun onSnackBarShown() {
        _uiStateFlow.update { state ->
            (state as UiState.Error<T>).copy(
                error = null
            )
        }
    }

    internal fun setSnackBarValue(value: UiText?) {
        _uiStateFlow.update {
            UiState.Error(
                error = value
            )
        }
    }

    // TODO: ideally, this method should come from a shared vm
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )
}
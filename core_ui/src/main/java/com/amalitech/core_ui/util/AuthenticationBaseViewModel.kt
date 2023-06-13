package com.amalitech.core_ui.util

import androidx.lifecycle.ViewModel
import com.amalitech.core.util.UiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class AuthenticationBaseViewModel<T> : ViewModel() {
    protected val baseResult: MutableStateFlow<UiState<T>?> =
        MutableStateFlow(null)
    val publicBaseResult = baseResult.asStateFlow()
    protected var job: Job? = null

    /**
     * onSnackBarShown - Reset snackBarValue to null
     */
    fun onSnackBarShown() {
        baseResult.update { state ->
            (state as UiState.Error<T>).copy(
                error = null
            )
        }
    }

    internal fun setSnackBarValue(value: UiText?) {
        baseResult.update {
            UiState.Error(
                error = value
            )
        }
    }
}
package com.amalitech.core_ui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BookMeetingRoomViewModel() : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        /**
         * A [CoroutineExceptionHandler] can be called from any thread. So, if [block] is supposed to
         * run in the main thread, you need to be careful and call this function on a scope that
         * runs in the main thread, such as a [viewModelScope] for viewmodels.
         */
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )
}
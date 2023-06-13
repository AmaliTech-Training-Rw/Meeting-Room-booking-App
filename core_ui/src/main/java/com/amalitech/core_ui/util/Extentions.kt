package com.amalitech.core_ui.util

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    Log.e("createExceptionHandler", "$throwable ... $message: ", )
    throwable.printStackTrace()

    /**
     * A [CoroutineExceptionHandler] can be called from any thread. So, if [action] is supposed to
     * run in the main thread, you need to be careful and call this function on the a scope that
     * runs in the main thread, such as a [viewModelScope].
     */
    launch {
        action(throwable)
    }
}

// TODO: how to use: method 1
//    fun doLogin() {
//        val errorMessage = "Failed to login"
//        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
//
//        viewModelScope.launch(exceptionHandler) {
//           // do login
//        }
//    }
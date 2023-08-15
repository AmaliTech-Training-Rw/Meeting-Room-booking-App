package com.tradeoases.invite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.tradeoases.invite.usecases.GetInviteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InvitesViewModel (
    private val getInviteUseCase: GetInviteUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(
        InviteViewState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        subscribeToInviteUpdates()
    }

    private fun subscribeToInviteUpdates() {
        launchCatching {
            getInviteUseCase().collect { invite ->
                val updatedInviteSet = (uiState.value.invite + invite).toSet() // remove dups
                _uiState.update { oldState ->
                    oldState.copy( loading = false, invite = updatedInviteSet.toList())
                }
            }
        }
    }

    // TODO: ideally, this method should come from a share vm
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
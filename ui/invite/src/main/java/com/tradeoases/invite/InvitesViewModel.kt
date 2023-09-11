package com.tradeoases.invite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tradeoases.invite.usecases.GetInviteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InvitesViewModel(
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
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            val flow = getInviteUseCase()
            flow.data?.let { listFlow ->
                val stateFlow = listFlow.stateIn(viewModelScope)
                _uiState.update {
                    it.copy(
                        invite = stateFlow,
                        loading = false
                    )
                }
            }
            flow.error?.let { error ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = error
                    )
                }
            }
            if (flow.error == null && flow.data == null) {
                _uiState.update { it.copy(loading = false) }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
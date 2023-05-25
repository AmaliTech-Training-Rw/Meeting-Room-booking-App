package com.amalitech.bottom_navigation

import androidx.lifecycle.ViewModel
import com.amalitech.bottom_navigation.use_case.GetInvitationsNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BottomNavigationViewModel(
    getInvitationsNumber: GetInvitationsNumber
) : ViewModel() {

    private val _invitation: MutableStateFlow<Int?> = MutableStateFlow(
        null
    )
    val invitations = _invitation.asStateFlow()

    init {
        _invitation.update {
            getInvitationsNumber()
        }
    }
}

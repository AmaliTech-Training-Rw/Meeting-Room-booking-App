package com.tradeoases.invite

import com.amalitech.core.util.UiText
import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


data class InviteViewState(
    val loading: Boolean = true,
    val invite: StateFlow<List<Invite>> = MutableStateFlow(emptyList()),
    val error: UiText? = null
)
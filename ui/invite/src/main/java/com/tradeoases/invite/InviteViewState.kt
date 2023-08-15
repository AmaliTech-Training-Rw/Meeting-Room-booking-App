package com.tradeoases.invite

import com.tradeoases.invite.models.Invite


data class InviteViewState(
    val loading: Boolean = true,
    val invite: List<Invite> = emptyList()
)

data class InviteUiState(
    val inviteId: Int = 0,
    val roomName: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val imageUrl: String = ""
)

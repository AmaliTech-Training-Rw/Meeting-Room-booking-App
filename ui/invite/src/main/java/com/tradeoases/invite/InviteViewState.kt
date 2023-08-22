package com.tradeoases.invite

import com.tradeoases.invite.models.Invite


data class InviteViewState(
    val loading: Boolean = true,
    val invite: List<Invite> = emptyList()
)
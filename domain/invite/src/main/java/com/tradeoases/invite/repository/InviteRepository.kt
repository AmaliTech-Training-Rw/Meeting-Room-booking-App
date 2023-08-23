package com.tradeoases.invite.repository

import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.flow.Flow

interface InviteRepository {
    suspend fun getInvites(): Flow<Invite>
}
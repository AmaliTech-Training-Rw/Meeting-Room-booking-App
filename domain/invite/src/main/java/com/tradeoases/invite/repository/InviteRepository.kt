package com.tradeoases.invite.repository

import com.amalitech.core.util.ApiResult
import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.flow.Flow

interface InviteRepository {
    suspend fun getInvites(): ApiResult<Flow<List<Invite>>>
}
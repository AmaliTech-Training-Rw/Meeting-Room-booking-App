package com.tradeoases.invite.repository

import com.amalitech.core.data.repository.BaseRepo
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.extractError
import com.tradeoases.invite.data_source.remote.InviteApiService
import com.tradeoases.invite.data_source.remote.dto.InviteDto
import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InviteRepositoryImpl(
    private val api: InviteApiService
) : InviteRepository, BaseRepo() {
    override suspend fun getInvites(): ApiResult<Flow<List<Invite>>> {
        return try {
            var result: ApiResult<InviteDto> = ApiResult()
            val flow = flow {
                while (true) {
                    result = safeApiCall(
                        apiToBeCalled = {
                            api.fetchInvitations()
                        },
                        extractError = {
                            extractError(it)
                        }
                    )
                    val list = result.data?.data?.map { it.toInvite() }
                    list?.let {
                        emit(it)
                    }
                    delay(5000)
                }
            }
            ApiResult(data = flow, result.error)
        } catch (e: Exception) {
            ApiResult(error = e.extractError())
        }
    }
}

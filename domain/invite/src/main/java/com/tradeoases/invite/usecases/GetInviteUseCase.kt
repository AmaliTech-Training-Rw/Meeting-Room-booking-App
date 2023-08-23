package com.tradeoases.invite.usecases

import com.tradeoases.invite.repository.InviteRepository

class GetInviteUseCase constructor(
    private val inviteRepository: InviteRepository
) {
    suspend operator fun invoke() = inviteRepository.getInvites()
}


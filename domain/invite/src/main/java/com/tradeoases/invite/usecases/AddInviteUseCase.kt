package com.tradeoases.invite.usecases

import com.tradeoases.invite.models.Invite
import com.tradeoases.invite.repository.InviteRepository

class AddInviteUseCase constructor(
    private val inviteRepository: InviteRepository
) {
    suspend operator fun invoke(
        invite: Invite
    ) = inviteRepository.addInvite(invite)
}

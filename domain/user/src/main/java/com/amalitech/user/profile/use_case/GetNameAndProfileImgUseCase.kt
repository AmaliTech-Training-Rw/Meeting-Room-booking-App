package com.amalitech.user.profile.use_case

import com.amalitech.user.repository.UserRepository

class GetNameAndProfileImgUseCase(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.userInfo
}

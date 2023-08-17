package com.amalitech.user.profile.use_case

import com.amalitech.core.domain.ValidatePasswordUseCase
import com.amalitech.core.domain.use_case.CheckPasswordsMatchUseCase
import com.amalitech.core.domain.use_case.CheckValuesNotBlankUseCase

data class ProfileUseCaseWrapper(
    val getUserUseCase: GetUserUseCase,
    val saveUserUseCase: SaveUserUseCase,
    val updateProfileUseCase: UpdateProfileUseCase,
    val checkPasswordsMatchUseCase: CheckPasswordsMatchUseCase,
    val checkValuesNotBlankUseCase: CheckValuesNotBlankUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase
)

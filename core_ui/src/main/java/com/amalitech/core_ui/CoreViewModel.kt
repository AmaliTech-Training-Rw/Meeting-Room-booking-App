package com.amalitech.core_ui

import androidx.lifecycle.ViewModel
import com.amalitech.user.profile.use_case.GetNameAndProfileImgUseCase

class CoreViewModel(
    getNameAndProfileImgUseCase: GetNameAndProfileImgUseCase,
): ViewModel() {
    val userInfo = getNameAndProfileImgUseCase()
}

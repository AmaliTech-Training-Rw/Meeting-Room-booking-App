package com.amalitech.core_ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.user.profile.use_case.GetUserUseCase
import kotlinx.coroutines.launch

class CoreViewModel(
    private val sharedPreferences: OnboardingSharedPreferences,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private val _userProfileImgUrl = mutableStateOf("")
    val userProfileImgUrl: State<String> = _userProfileImgUrl
    private val _userFullName = mutableStateOf("")
    val userFullName: State<String> = _userFullName


    init {
        loadUserInfos()
    }

    fun loadUserInfos() {
        viewModelScope.launch {
            val userEmailAdd = sharedPreferences.loadLoggedInUserEmail()
            val response = getUserUseCase(email = userEmailAdd)
            _userProfileImgUrl.value = response.data?.profileImgUrl ?: ""
            _userFullName.value =
                "${response.data?.firstName ?: ""} ${response.data?.lastName ?: ""}"
        }
    }
}

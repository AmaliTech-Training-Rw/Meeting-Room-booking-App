package com.amalitech.user.profile.update_profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.ui.user.R
import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateProfileViewModel(
    private val useCase: ProfileUseCaseWrapper
) : BaseViewModel<Nothing>() {
    private val _uiState = MutableStateFlow(UpdateProfileUiState())
    val uiState: StateFlow<UpdateProfileUiState> get() = _uiState.asStateFlow()

    fun onNewProfileImage(imageUri: Uri?) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(profileImage = imageUri))
        }
    }

    fun onNewFirstName(name: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(firstName = name))
        }
    }

    fun onNewLastName(name: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(lastName = name))
        }
    }

    private fun onNewEmail(email: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(email = email))
        }
    }

    fun onNewTitle(title: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(title = title))
        }
    }

    fun onOldPasswordEntered(password: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(oldPassword = password))
        }
    }

    fun onNewPasswordEntered(password: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(newPassword = password))
        }
    }

    fun onNewPasswordConfirmationEntered(password: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(newPasswordConfirmation = password))
        }
    }

    private fun onNewProfileImageUrl(link: String) {
        _uiState.update {
            it.copy(profileUserInput = it.profileUserInput.copy(profileImageUrl = link))
        }
    }

    fun updateProfile(context: Context) {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val passwordNotBlank = useCase.checkValuesNotBlankUseCase(
                _uiState.value.profileUserInput.newPassword
            )
            var passwordValid: UiText? = null
            if (passwordNotBlank == null) {
                passwordValid = useCase.checkPasswordsMatchUseCase(
                    _uiState.value.profileUserInput.newPassword,
                    _uiState.value.profileUserInput.newPasswordConfirmation
                )
                if (passwordValid == null)
                    passwordValid = useCase.validatePasswordUseCase(_uiState.value.profileUserInput.newPassword)
            }

            val valuesBlank = useCase.checkValuesNotBlankUseCase(
                _uiState.value.profileUserInput.firstName,
                _uiState.value.profileUserInput.lastName,
                _uiState.value.profileUserInput.title,
            )
            if (passwordValid == null && valuesBlank == null) {
                val result = useCase.updateProfileUseCase(_uiState.value.profileUserInput.toProfile(), context = context)

                result.data?.let { userProfile ->
                    useCase.saveUserUseCase(
                        UserDto(
                            uid = userProfile.id,
                            firstName = userProfile.firstName,
                            lastName = userProfile.lastName,
                            email = userProfile.email,
                            title = userProfile.title,
                            profileImgUrl = userProfile.profileImgUrl
                        )
                    )
                    _uiState.update {
                        it.copy(isLoading = false, canNavigate = true)
                    }
                }
                result.error?.let {
                    _uiState.update {
                        it.copy(error = result.error, isLoading = false)
                    }
                }
            } else if (passwordValid != null) {
                _uiState.update {
                    it.copy(error = passwordValid, isLoading = false)
                }
            } else {
                _uiState.update {
                    it.copy(error = valuesBlank, isLoading = false)
                }
            }
        }
    }

    fun setValues(email: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val user = useCase.getUserUseCase(email = email)
            val data = user.data
            if (data != null) {
                onNewEmail(data.email)
                onNewFirstName(data.firstName)
                onNewLastName(data.lastName)
                onNewTitle(data.title)
                onNewProfileImageUrl(data.profileImgUrl)
                onNewProfileImage(null)

                _uiState.update {
                    it.copy(isLoading = false)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UiText.StringResource(R.string.error_unable_to_load_your_profile_information)
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

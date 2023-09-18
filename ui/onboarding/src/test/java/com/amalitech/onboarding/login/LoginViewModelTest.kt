package com.amalitech.onboarding.login

import com.amalitech.core.R
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.Response
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.MainDispatcherRule
import com.amalitech.core.domain.model.UserProfile
import com.amalitech.onboarding.login.use_case.LoginUseCasesWrapper
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var loginUseCasesWrapper: LoginUseCasesWrapper

    @MockK
    private lateinit var sharedPreferences: OnboardingSharedPreferences

    @MockK
    private lateinit var userProfileUseCaseWrapper: ProfileUseCaseWrapper

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        loginUseCasesWrapper = mockk()
        sharedPreferences = mockk()
        every { sharedPreferences.loadAdminUserScreen() } returns true
        userProfileUseCaseWrapper = mockk()
        coJustRun {
            userProfileUseCaseWrapper.saveUserUseCase(any())
        }
        viewModel =
            LoginViewModel(sharedPreferences, userProfileUseCaseWrapper, loginUseCasesWrapper)
    }

    @Test
    fun `on new email called with any string state is updated`() {
        val email = "test@email.com "

        viewModel.onNewEmail(email)

        assertEquals(email.trim(), viewModel.uiState.value.email)
    }

    @Test
    fun `on new password called with any string state is updated`() {
        val password = "test "

        viewModel.onNewPassword(password)

        assertEquals(password.trim(), viewModel.uiState.value.password)
    }

    @Test
    fun `when onLoginClick is called with valid email and password the state is updated accordingly`() {
        every {
            loginUseCasesWrapper.validateEmailUseCase(any())
        } returns null

        every {
            loginUseCasesWrapper.validatePasswordUseCase(any())
        } returns null

        justRun { sharedPreferences.saveToken(any()) }
        coEvery {
            loginUseCasesWrapper.logInUseCase(any(), any())
        } returns ApiResult(
            data = UserProfile(
                email = "email",
                firstName = "Ngomdé Cadet",
                lastName = "Kamdaou",
                title = "Android dev",
                profileImgUrl = "https://via.placeholder.com/400.png",
                id = 2,
                isAdmin = 1,
                locationId = 2,
                organisationId = 2,
                userId = 1,
                username = ""
            )
        )

        every {
            loginUseCasesWrapper.isUserAdminUseCase()
        } returns false

        justRun {
            sharedPreferences.saveUserType(any())
        }
        justRun {
            sharedPreferences.saveShouldShowOnboarding(any())
        }
        justRun { sharedPreferences.saveAdminUserScreen(any()) }
        coEvery { loginUseCasesWrapper.loadProfileInformationUseCase(any()) } returns Response(
            data = UserProfile(
                email = "email",
                firstName = "Ngomdé Cadet",
                lastName = "Kamdaou",
                title = "Android dev",
                profileImgUrl = "https://via.placeholder.com/400.png",
                id = 2,
                isAdmin = 1,
                locationId = 2,
                organisationId = 2,
                userId = 1,
                username = ""
            )
        )
        justRun { sharedPreferences.saveLoggedInUserEmail(any()) }

        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Success)
    }

    @Test
    fun `when on login click is called with invalid email and valid password an error is added to state`() {
        every {
            loginUseCasesWrapper.validateEmailUseCase(any())
        } returns UiText.StringResource(R.string.error_email_not_valid)

        every {
            loginUseCasesWrapper.validatePasswordUseCase(any())
        } returns null

        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(R.string.error_email_not_valid),
            (state.value as UiState.Error).error
        )
    }

    @Test
    fun `when onLoginClick is called with wrong credentials an error is added to state`() {
        every {
            loginUseCasesWrapper.validateEmailUseCase(any())
        } returns null

        every {
            loginUseCasesWrapper.validatePasswordUseCase(any())
        } returns null

        coEvery {
            loginUseCasesWrapper.logInUseCase(any(), any())
        } returns ApiResult(
            error = UiText.StringResource(androidx.compose.ui.R.string.default_error_message)
        )


        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            (state.value as UiState.Error).error
        )
    }
}

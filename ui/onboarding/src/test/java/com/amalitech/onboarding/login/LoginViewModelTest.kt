package com.amalitech.onboarding.login

import com.amalitech.core.util.UiText
import com.amalitech.core.R
import com.amalitech.onboarding.MainDispatcherRule
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.login.use_case.LoginUseCasesWrapper
import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var loginUseCasesWrapper: LoginUseCasesWrapper

    @MockK
    private lateinit var sharedPreferences: OnboardingSharedPreferences

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        loginUseCasesWrapper = mockk()
        sharedPreferences = mockk()
        viewModel = LoginViewModel(loginUseCasesWrapper, sharedPreferences)
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

        every {
            loginUseCasesWrapper.logInUseCase(any(), any())
        } returns null

        every {
            loginUseCasesWrapper.isUserAdminUseCase()
        } returns false

        justRun {
            sharedPreferences.saveUserType(any())
        }
        justRun {
            sharedPreferences.saveShouldShowOnboarding(any())
        }

        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Success)
    }

    @Test
    fun ` when onLoginClick is called with valid email and invalid password an error is added to state`() {
        every {
            loginUseCasesWrapper.validateEmailUseCase(any())
        } returns null

        every {
            loginUseCasesWrapper.validatePasswordUseCase(any())
        } returns UiText.StringResource(R.string.error_password_is_not_valid)

        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(R.string.error_password_is_not_valid),
            (state.value as UiState.Error).error
        )
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

        every {
            loginUseCasesWrapper.logInUseCase(any(), any())
        } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)


        viewModel.onLoginClick()

        val state = viewModel.uiStateFlow
        assertTrue(state.value is UiState.Error)
        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            (state.value as UiState.Error).error
        )
    }
}

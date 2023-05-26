package com.amalitech.onboarding.login

import com.amalitech.core.util.UiText
import com.amalitech.domain.onboarding.R
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var loginUseCase: com.amalitech.onboarding.login.use_case.LoginUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        loginUseCase = mockk()
        viewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
    fun `when a snackBar is shown, its value is cleared in state`() {
        viewModel.onSnackBarShown()

        assertEquals(null, viewModel.uiState.value.snackBarValue)
    }

    @Test
    fun `when onLoginClick is called with valid email and password the state is updated accordingly`() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns null

        every {
            loginUseCase.logIn(any(), any())
        } returns null

        viewModel.onLoginClick()

        val state = viewModel.uiState
        assertEquals(
            true, state.value.finishedLoggingIn
        )
        assertEquals(
            UiText.StringResource(com.amalitech.ui.onboarding.R.string.logged_in_successfully),
            state.value.snackBarValue
        )
    }

    @Test
    fun ` when onLoginClick is called with valid email and invalid password an error is added to state`() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns UiText.StringResource(R.string.error_password_is_blank)

        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(R.string.error_password_is_blank), viewModel.uiState.value.error
        )
    }

    @Test
    fun `when on login click is called with invalid email and valid password an error is added to state`() {
        every {
            loginUseCase.validateEmail(any())
        } returns UiText.StringResource(R.string.error_email_not_valid)

        every {
            loginUseCase.validatePassword(any())
        } returns null

        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(R.string.error_email_not_valid), viewModel.uiState.value.error
        )
    }

    @Test
    fun `when onLoginClick is called with wrong credentials an error is added to state`() {
        every {
            loginUseCase.validateEmail(any())
        } returns null

        every {
            loginUseCase.validatePassword(any())
        } returns null

        every {
            loginUseCase.logIn(any(), any())
        } returns UiText.StringResource(androidx.compose.ui.R.string.default_error_message)


        viewModel.onLoginClick()

        assertEquals(
            UiText.StringResource(androidx.compose.ui.R.string.default_error_message),
            viewModel.uiState.value.error
        )
    }
}

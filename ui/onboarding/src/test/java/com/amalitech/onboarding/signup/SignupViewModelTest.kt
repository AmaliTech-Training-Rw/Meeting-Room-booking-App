package com.amalitech.onboarding.signup

import com.amalitech.core.util.UiText
import com.amalitech.core.R
import com.amalitech.onboarding.MainDispatcherRule
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import com.amalitech.onboarding.util.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @MockK
    private lateinit var signupUseCase: SignupUseCase

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        signupUseCase = mockk()
        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())
        viewModel = SignupViewModel(signupUseCase)
    }

    @Test
    fun `ensures email is held by state`() {
        val email = "test@email.com"

        viewModel.onNewEmail(email)

        assertEquals(email, viewModel.uiState.value.email)
    }

    @Test
    fun `ensures password is held by state`() {
        val password = "password"

        viewModel.onNewPassword(password)

        assertEquals(password, viewModel.uiState.value.password)
    }

    @Test
    fun `ensures username is held by state`() {
        val username = "username"

        viewModel.onNewUsername(username)

        assertEquals(username, viewModel.uiState.value.username)
    }

    @Test
    fun `ensures location is held by state`() {
        val location = "location"

        viewModel.onNewLocation(location)

        assertEquals(location, viewModel.uiState.value.location)
    }

    @Test
    fun `ensures organizationName is held by state`() {
        val organizationName = "organizationName"

        viewModel.onNewOrganizationName(organizationName)

        assertEquals(organizationName, viewModel.uiState.value.organizationName)
    }

    @Test
    fun `ensures passwordConfirmation is held by state`() {
        val passwordConfirmation = "passwordConfirmation"
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null

        viewModel.onNewPasswordConfirmation(passwordConfirmation)

        assertEquals(passwordConfirmation, viewModel.uiState.value.passwordConfirmation)
    }

    @Test
    fun `ensures selectedOrganizationTypeId is held by state`() {
        val selectedOrganizationType = "organization1"

        viewModel.onSelectedOrganizationType(selectedOrganizationType)

        assertEquals(selectedOrganizationType, viewModel.uiState.value.selectedOrganizationType)
    }

    @Test
    fun `ensures no error is thrown when data are valid`() {
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(null, viewModel.uiState.value.error)
        assertEquals(
            UiText.StringResource(R.string.your_account_is_created),
            viewModel.uiState.value.snackBarValue
        )
        assertEquals(true, viewModel.uiState.value.finishedSigningUp)
    }

    @Test
    fun `ensures an error is thrown when signup failed`() {
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns UiText.StringResource(R.string.your_account_is_created)

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(
            UiText.StringResource(R.string.your_account_is_created),
            viewModel.uiState.value.error
        )
    }

    @Test
    fun `ensures error is thrown when email is not valid`() {
        val error = UiText.StringResource(R.string.error_email_not_valid)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null

        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns error
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when password is not valid`() {
        val error = UiText.StringResource(R.string.error_password_is_not_valid)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null

        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns error
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when passwords don't match`() {
        val error = UiText.StringResource(R.string.error_passwords_dont_match)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns error
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when email is not available`() {
        val error =
            UiText.StringResource(R.string.error_email_address_already_taken)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns false
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when at least one field is not blank`() {
        val error =
            UiText.StringResource(R.string.error_email_not_valid)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns false
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns error

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when username is not available`() {
        val error =
            UiText.StringResource(R.string.error_username_address_already_taken)
        viewModel.onSelectedOrganizationType("organization2")

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null
        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns false

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is thrown when no organization is selected is not available`() {
        val error =
            UiText.StringResource(R.string.error_no_organization_type_selected)
        every {
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns null

        every {
            signupUseCase.validateEmail(any())
        } returns null
        every {
            signupUseCase.validatePassword(any())
        } returns null
        every {
            signupUseCase.isEmailAvailable(any())
        } returns true
        every {
            signupUseCase.isUsernameAvailable(any())
        } returns true

        every {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganization()
        } returns Result.Success(listOf())

        viewModel.onSignupClick()

        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `fetchOrganizations() should update the loading status`() = runTest {
        coEvery {
            signupUseCase.fetchOrganization()
        } coAnswers {
            delay(1000)
            Result.Success(listOf())
        }

        viewModel.fetchOrganizations()

        assertEquals(Result.Loading, viewModel.uiState.value.typeOfOrganization)
        advanceUntilIdle()
        assertEquals(Result.Success<String>(listOf()), viewModel.uiState.value.typeOfOrganization)
    }
}

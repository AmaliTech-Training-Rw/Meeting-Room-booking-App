package com.amalitech.onboarding.signup

import com.amalitech.core.R
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.MainDispatcherRule
import com.amalitech.onboarding.signup.use_case.SignupUseCasesWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @MockK
    private lateinit var signupUseCasesWrapper: SignupUseCasesWrapper

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        signupUseCasesWrapper = mockk()
        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())
        coEvery {
            signupUseCasesWrapper.fetchLocationsUseCase()
        } returns ApiResult(data = listOf())
        viewModel = SignupViewModel(signupUseCasesWrapper)
    }

    @Test
    fun `ensures email is held by state`() {
        val email = "test@email.com"

        viewModel.onNewEmail(email)

        assertEquals(email, viewModel.userInput.value.email)
    }

    @Test
    fun `ensures password is held by state`() {
        val password = "password"

        viewModel.onNewPassword(password)

        assertEquals(password, viewModel.userInput.value.password)
    }

    @Test
    fun `ensures username is held by state`() {
        val username = "username"

        viewModel.onNewUsername(username)

        assertEquals(username, viewModel.userInput.value.username)
    }

    @Test
    fun `ensures location is held by state`() {
        val location = 1

        viewModel.onLocationSelected(location)

        assertEquals(location, viewModel.userInput.value.location)
    }

    @Test
    fun `ensures organizationName is held by state`() {
        val organizationName = "organizationName"

        viewModel.onNewOrganizationName(organizationName)

        assertEquals(organizationName, viewModel.userInput.value.organizationName)
    }

    @Test
    fun `ensures passwordConfirmation is held by state`() {
        val passwordConfirmation = "passwordConfirmation"
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null

        viewModel.onNewPasswordConfirmation(passwordConfirmation)

        assertEquals(passwordConfirmation, viewModel.userInput.value.passwordConfirmation)
    }

    @Test
    fun `ensures selectedOrganizationTypeId is held by state`() {
        val selectedOrganizationType = 1

        viewModel.onSelectedOrganizationType(selectedOrganizationType)

        assertEquals(selectedOrganizationType, viewModel.userInput.value.selectedOrganizationType)
    }

    @Test
    fun `ensures no error is thrown when data are valid`() {
        viewModel.onSelectedOrganizationType(2)
        viewModel.onLocationSelected(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(
            true,
            (viewModel.uiStateFlow.value as UiState.Success).data?.shouldNavigate
        )
    }

    @Test
    fun `ensures an error is thrown when signup failed`() {
        viewModel.onSelectedOrganizationType(2)
        viewModel.onLocationSelected(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns UiText.StringResource(R.string.your_account_is_created)

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            UiText.StringResource(R.string.your_account_is_created),
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when email is not valid`() {
        val error = UiText.StringResource(R.string.error_email_not_valid)
        viewModel.onSelectedOrganizationType(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null

        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns error
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when password is not valid`() {
        val error = UiText.StringResource(R.string.error_password_is_not_valid)
        viewModel.onSelectedOrganizationType(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null

        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns error
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when passwords don't match`() {
        val error = UiText.StringResource(R.string.error_passwords_dont_match)
        viewModel.onSelectedOrganizationType(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns error
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when email is not available`() {
        val error =
            UiText.StringResource(R.string.error_email_address_already_taken)
        viewModel.onSelectedOrganizationType(2)
        viewModel.onLocationSelected(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns false
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when at least one field is not blank`() {
        val error =
            UiText.StringResource(R.string.error_email_not_valid)
        viewModel.onSelectedOrganizationType(2)

        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns false
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns error

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when username is not available`() {
        val error =
            UiText.StringResource(R.string.error_username_address_already_taken)
        viewModel.onSelectedOrganizationType(2)
        viewModel.onLocationSelected(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns false

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `ensures error is thrown when no organization is selected is not available`() {
        val error =
            UiText.StringResource(R.string.error_no_organization_type_selected)
        viewModel.onSelectedOrganizationType(-1)
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null

        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        assertTrue(viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(
            error,
            (viewModel.uiStateFlow.value as UiState.Error).error
        )
    }

    @Test
    fun `Ensure isInvitedUser works when all data are provided`() {
        val token = "email@test.com"

        val response =
            viewModel.isInvitedUser(token)

        assertEquals(true, response)
    }

    @Test
    fun `Ensure isInvitedUser works when not all data are provided`() {
        val token = ""

        val response =
            viewModel.isInvitedUser(token)

        assertEquals(false, response)
    }

    @Test
    fun `ensure args are validated`() {
        val token = "email@test.com"


        viewModel.submitValues(
            token = token,
        )

        assertEquals(
            token, viewModel.userInput.value.token
        )
    }

    @Test
    fun `ensure isInvitedUser returns false when any arg is blank`() {
        val token = ""

        val result = viewModel.isInvitedUser(
            token = token
        )

        assertEquals(false, result)
    }

    @Test
    fun `ensure isInvitedUser returns false when any arg is null`() {
        val result = viewModel.isInvitedUser(
            token = null
        )

        assertEquals(
            false,
            result
        )
    }

    @Test
    fun `ensure isInvitedUser returns true when all args are valid`() {
        val token = "yes"

        val result = viewModel.isInvitedUser(token)


        assertEquals(
            true,
            result
        )
    }

    @Test
    fun `ensure that the job is not null once onsignupclick is called`() = runTest {
        viewModel.onSelectedOrganizationType(2)

        every {
            signupUseCasesWrapper.checkValuesNotBlankUseCase(
                any(),
                any(),
                any(),
                any()
            )
        } returns null
        every {
            signupUseCasesWrapper.checkPasswordsMatchUseCase(any(), any())
        } returns null
        every {
            signupUseCasesWrapper.validateEmailUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.validatePasswordUseCase(any())
        } returns null
        every {
            signupUseCasesWrapper.isEmailAvailableUseCase(any())
        } returns true
        every {
            signupUseCasesWrapper.isUsernameAvailableUseCase(any())
        } returns true

        coEvery {
            signupUseCasesWrapper.signupUseCase(any())
        } returns null

        coEvery {
            signupUseCasesWrapper.fetchOrganizationsTypeUseCase()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        advanceUntilIdle()
        assertEquals(false, viewModel.isJobActive())
    }
}

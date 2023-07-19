package com.amalitech.onboarding.signup

import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.onboarding.MainDispatcherRule
import com.amalitech.onboarding.signup.use_case.SignupUseCase
import com.amalitech.core.util.ApiResult
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
    private lateinit var signupUseCase: SignupUseCase

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        signupUseCase = mockk()
        coEvery {
            signupUseCase.fetchOrganizationsType()
        } returns ApiResult(data = listOf())
        viewModel = SignupViewModel(signupUseCase)
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
        val location = "location"

        viewModel.onNewLocation(location)

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
            signupUseCase.checkPasswordsMatch(any(), any())
        } returns null

        viewModel.onNewPasswordConfirmation(passwordConfirmation)

        assertEquals(passwordConfirmation, viewModel.userInput.value.passwordConfirmation)
    }

    @Test
    fun `ensures selectedOrganizationTypeId is held by state`() {
        val selectedOrganizationType = "organization1"

        viewModel.onSelectedOrganizationType(selectedOrganizationType)

        assertEquals(selectedOrganizationType, viewModel.userInput.value.selectedOrganizationType)
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns UiText.StringResource(R.string.your_account_is_created)

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        every {
            signupUseCase.checkValuesNotBlank(any(), any(), any(), any(), any(), any())
        } returns error

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
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
        val organizationName = "name"
        val email = "email@test.com"
        val typeOfOrganization = "type"
        val location = "location"

        val response =
            viewModel.isInvitedUser(email, organizationName, location, typeOfOrganization)

        assertEquals(true, response)
    }

    @Test
    fun `Ensure isInvitedUser works when not all data are provided`() {
        val organizationName = ""
        val email = ""
        val typeOfOrganization = ""
        val location = ""

        val response =
            viewModel.isInvitedUser(email, organizationName, location, typeOfOrganization)

        assertEquals(false, response)
    }

    @Test
    fun `ensure args are validated`() {
        val organizationName = "name"
        val email = "email@test.com"
        val typeOfOrganization = "type"
        val location = "location"

        viewModel.submitValues(
            organizationName = organizationName,
            email = email,
            typeOfOrganization = typeOfOrganization,
            location = location
        )

        assertEquals(
            email, viewModel.userInput.value.email
        )
        assertEquals(
            organizationName, viewModel.userInput.value.organizationName
        )
        assertEquals(
            typeOfOrganization, viewModel.userInput.value.selectedOrganizationType
        )
        assertEquals(
            location, viewModel.userInput.value.location
        )
    }

    @Test
    fun `ensure isInvitedUser returns false when any arg is blank`() {
        val organizationName = "null"
        val email = ""
        val typeOfOrganization = "type"
        val location = "location"

        val result = viewModel.isInvitedUser(
            organizationName = organizationName,
            email = email,
            typeOfOrganization = typeOfOrganization,
            location = location
        )

        assertEquals(false, result)
    }

    @Test
    fun `ensure isInvitedUser returns false when any arg is null`() {
        val organizationName = null
        val email = "yes"
        val typeOfOrganization = "type"
        val location = "location"

        val result = viewModel.isInvitedUser(
            organizationName = organizationName,
            email = email,
            typeOfOrganization = typeOfOrganization,
            location = location
        )


        assertEquals(
            false,
            result
        )
    }

    @Test
    fun `ensure isInvitedUser returns true when all args are valid`() {
        val organizationName = "null"
        val email = "yes"
        val typeOfOrganization = "type"
        val location = "location"

        val result = viewModel.isInvitedUser(
            organizationName = organizationName,
            email = email,
            typeOfOrganization = typeOfOrganization,
            location = location
        )


        assertEquals(
            true,
            result
        )
    }

    @Test
    fun `ensure that the job is not null once onsignupclick is called`() = runTest {
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

        coEvery {
            signupUseCase.signup(any())
        } returns null

        coEvery {
            signupUseCase.fetchOrganizationsType()
        } returns ApiResult(data = listOf())

        viewModel.onSignupClick()

        advanceUntilIdle()
        assertEquals(false, viewModel.isJobActive())
    }
}

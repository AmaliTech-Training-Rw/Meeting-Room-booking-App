package com.amalitech.user.profile

import com.amalitech.core.R
import com.amalitech.core.domain.preferences.OnboardingSharedPreferences
import com.amalitech.core.util.Response
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.user.MainDispatcherRule
import com.amalitech.user.profile.model.dto.UserDto
import com.amalitech.user.profile.use_case.ProfileUseCaseWrapper
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel

    @MockK
    private lateinit var useCaseWrapper: ProfileUseCaseWrapper

    @MockK
    private lateinit var sharedPreferences: OnboardingSharedPreferences

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        useCaseWrapper = mockk()
        sharedPreferences = mockk()
    }

    @Test
    fun `ensures updateAdminUserScreen works`() {
        justRun { sharedPreferences.saveAdminUserScreen(any()) }

        viewModel = ProfileViewModel(useCaseWrapper, sharedPreferences)
        viewModel.updateAdminUserScreen(true)

        assertEquals(true, viewModel.isUsingAdminDashboard.value)
    }

    @Test
    fun `ensures getUser works, when there is no error`() {
        val email = "test@amalitech.org"
        val user = UserDto(
            uid = 0,
            firstName = "name",
            lastName = "name",
            email = email,
            title = "software engineer",
            profileImgUrl = ""
        )

        every {
            sharedPreferences.loadLoggedInUserEmail()
        } returns email
        coEvery {
            useCaseWrapper.getUserUseCase(any())
        } returns Response(user)
        every { sharedPreferences.isUserAdmin() } returns true
        every { sharedPreferences.loadAdminUserScreen() } returns true

        viewModel = ProfileViewModel(useCaseWrapper, sharedPreferences)
        viewModel.getUser()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Success)
        assertEquals(user, (viewModel.uiStateFlow.value as UiState.Success).data?.user)
    }

    @Test
    fun `ensures getUser works, when getUser returns an error`() {
        val email = "test@amalitech.org"
        val error = UiText.StringResource(R.string.error_email_not_valid)

        every {
            sharedPreferences.loadLoggedInUserEmail()
        } returns email
        coEvery {
            useCaseWrapper.getUserUseCase(any())
        } returns Response(error = error)
        justRun { sharedPreferences.isUserAdmin() }
        justRun { sharedPreferences.loadAdminUserScreen() }
        viewModel = ProfileViewModel(useCaseWrapper, sharedPreferences)
        viewModel.getUser()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(error, (viewModel.uiStateFlow.value as UiState.Error).error)
    }

    @Test
    fun `ensures getUser works, when getUser returns nothing`() {
        val email = "test@amalitech.org"
        val error = UiText.StringResource(R.string.error_default_message)

        every {
            sharedPreferences.loadLoggedInUserEmail()
        } returns email
        coEvery {
            useCaseWrapper.getUserUseCase(any())
        } returns Response()
        justRun { sharedPreferences.isUserAdmin() }
        justRun { sharedPreferences.loadAdminUserScreen() }
        viewModel = ProfileViewModel(useCaseWrapper, sharedPreferences)
        viewModel.getUser()

        assertEquals(true, viewModel.uiStateFlow.value is UiState.Error)
        assertEquals(error, (viewModel.uiStateFlow.value as UiState.Error).error)
    }

    @Test
    fun `ensures _isUsingAdminDashboard is updated properly, when user is not an admin`() {

        justRun {
            sharedPreferences.loadLoggedInUserEmail()
        }
        coJustRun {
            useCaseWrapper.getUserUseCase(any())
        }
        every { sharedPreferences.isUserAdmin() } returns false
        justRun { sharedPreferences.loadAdminUserScreen() }
        viewModel = ProfileViewModel(useCaseWrapper, sharedPreferences)
        viewModel.getUser()

        assertEquals(false, viewModel.isUsingAdminDashboard.value)
    }
}
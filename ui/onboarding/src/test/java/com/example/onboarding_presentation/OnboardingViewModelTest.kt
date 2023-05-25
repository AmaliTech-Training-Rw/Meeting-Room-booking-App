package com.example.onboarding_presentation

import com.example.oboarding_domain.preferences.OnboardingSharedPreferences
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    @MockK
    private lateinit var pref: OnboardingSharedPreferences

    private val unconfinedDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        pref = mockk()
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = OnboardingViewModel(pref)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun onEvent_onGetStartClick_stateUpdated() =
        runBlocking {
            justRun {
                pref.saveShouldShowOnboarding(any())
            }

            viewModel.onGetSarted()

            val state = viewModel.finishedSaving
            assertEquals(state.value, true)

        }
}
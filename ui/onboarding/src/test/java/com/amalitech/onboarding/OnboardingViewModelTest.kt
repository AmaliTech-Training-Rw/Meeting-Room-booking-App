package com.amalitech.onboarding

import com.amalitech.onboarding.preferences.OnboardingSharedPreferences
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    @MockK
    private lateinit var pref: OnboardingSharedPreferences

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        pref = mockk()
        viewModel = OnboardingViewModel(pref)
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
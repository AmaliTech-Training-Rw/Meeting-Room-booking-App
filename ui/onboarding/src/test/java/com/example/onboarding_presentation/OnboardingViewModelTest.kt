package com.example.onboarding_presentation

import com.amalitech.core.util.UiEvents
import com.example.oboarding_domain.preferences.IPreferences
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    @MockK
    private lateinit var pref: IPreferences

    private val unconfinedDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        pref = mockk()
        viewModel = OnboardingViewModel(pref, unconfinedDispatcher)
    }

    @Test
    fun onEvent_onGetStartClick_navigationEventSent() =
        runBlocking {
            justRun {
                pref.saveShouldShowOnboarding(any())
            }

            viewModel.onEvent(OnboardingEvent.OnGetStartClick)

            viewModel.uiEvent.take(1)
                .collect {
                    assertEquals(UiEvents.NavigateToLogin, it)
                }
        }
}
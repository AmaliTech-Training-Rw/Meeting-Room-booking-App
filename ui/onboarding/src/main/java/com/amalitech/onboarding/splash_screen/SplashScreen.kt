package com.amalitech.onboarding.splash_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.onboarding.components.SplashAnimation
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    onNavigate: (isUserAdmin: Boolean) -> Unit,
    viewModel: SplashScreenViewModel = koinViewModel(),
    onComposing: (AppBarState) -> Unit
) {
    var isAnimationVisible by remember {
        mutableStateOf(true)
    }
    val canShowFadeOutAnim = remember {
        mutableStateOf(false)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        onComposing(AppBarState(hasTopBar = false))
    }

    SplashAnimation(
        onNavigate = { onNavigate(state.isUserAdmin) },
        canShowFadeoutAnim = canShowFadeOutAnim,
        onVisibilityChange = {
            isAnimationVisible = it
            canShowFadeOutAnim.value = !it
        },
        hasFinishedChecking = state.hasFinishedChecking
    )
}

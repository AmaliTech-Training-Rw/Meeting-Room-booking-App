package com.amalitech.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.core.R
import kotlinx.coroutines.delay

@Composable
fun SplashAnimation(
    canShowFadeoutAnim: MutableState<Boolean>,
    onNavigate: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    drawable: Painter = painterResource(id = R.drawable.logo),
    onVisibilityChange: (Boolean) -> Unit,
    hasFinishedChecking: Boolean = false,
    scaleAnimationDuration: Int = 1000,
    delayBeforeFadeOutAnimation: Int = 2000,
    fadeOutAnimationDuration: Int = 500

) {
    val scale = remember {
        Animatable(15f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            1f,
            animationSpec = tween(
                durationMillis = scaleAnimationDuration,
            )
        )
        // This is a default delay value. After that, if the viewModel did not retrieve all
        // the needed information, we will wait for it to finish before we navigate
        delay(delayBeforeFadeOutAnimation.toLong())

        if (hasFinishedChecking) {
            onVisibilityChange(false)
            delay(fadeOutAnimationDuration.toLong())
            onNavigate()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = !canShowFadeoutAnim.value,
            exit = fadeOut(
                animationSpec = tween(
                    fadeOutAnimationDuration
                )
            ),
            enter = EnterTransition.None
        ) {
            Image(
                painter = drawable,
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                    .scale(scale.value),
                colorFilter = ColorFilter.tint(tintColor)
            )
        }
    }
}

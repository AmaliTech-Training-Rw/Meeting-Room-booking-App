package com.amalitech.bookmeetingroom.ui.components

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
import com.amalitech.bookmeetingroom.R
import kotlinx.coroutines.delay

@Composable
fun SplashAnimation(
    onNavigate: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    drawable: Painter = painterResource(id = R.drawable.logo),
    canShowFadeoutAnim: MutableState<Boolean>,
    onVisibilityChange: (Boolean) -> Unit,
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
        delay(delayBeforeFadeOutAnimation.toLong())
        onVisibilityChange(false)
        delay(fadeOutAnimationDuration.toLong())
        onNavigate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = canShowFadeoutAnim.value,
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

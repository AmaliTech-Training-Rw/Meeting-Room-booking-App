package com.amalitech.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.amalitech.core.R
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.onboarding.components.ImageWithLegend
import com.amalitech.onboarding.components.SlidingDots
import kotlin.math.roundToInt

@Composable
fun OnboardingScreen(
    onComposing: (AppBarState) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    var currentIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        onComposing(AppBarState(hasTopBar = false))
    }

    when (currentIndex) {
        0 -> {
            OnBoard(
                selectedIndex = currentIndex,
                logo = R.drawable.logo,
                painter = painterResource(R.drawable.claudy),
                title = R.string.book_or_reserve_a_room,
                description = R.string.simple_and_direct_way_to_invite,
                onSwipe = {
                    currentIndex = it
                },
                onGetStartedClick = {
                    onNavigateToLogin()
                }
            )
        }

        1 -> {
            OnBoard(
                selectedIndex = currentIndex,
                logo = null,
                painter = painterResource(R.drawable.claudy),
                title = null,
                description = R.string.book_your_meeting_from_anywhere,
                onSwipe = {
                    currentIndex = it
                },
                onGetStartedClick = {
                    onNavigateToLogin()
                }
            )
        }

        2 -> {
            OnBoard(
                selectedIndex = currentIndex,
                logo = null,
                painter = painterResource(R.drawable.claudy),
                title = null,
                description = R.string.invite_attendees_to_the_booked_room,
                onSwipe = {
                    currentIndex = it
                },
                onGetStartedClick = {
                    onNavigateToLogin()
                }
            )
        }

        3 -> {
            OnBoard(
                selectedIndex = currentIndex,
                logo = null,
                painter = painterResource(R.drawable.claudy),
                title = null,
                description = R.string.synchronize_with_google_calendar,
                onSwipe = {
                    currentIndex = it
                },
                onGetStartedClick = {
                    onNavigateToLogin()
                }
            )
        }

        else -> {
            OnBoard(
                selectedIndex = currentIndex,
                logo = R.drawable.logo,
                painter = painterResource(R.drawable.claudy),
                title = R.string.book_or_reserve_a_room,
                description = R.string.simple_and_direct_way_to_invite,
                onSwipe = {
                    currentIndex = it
                },
                onGetStartedClick = {
                    onNavigateToLogin()
                }
            )
        }
    }
}

@Composable
fun OnBoard(
    selectedIndex: Int,
    @DrawableRes logo: Int?,
    painter: Painter,
    @StringRes title: Int?,
    @StringRes description: Int,
    onSwipe: (Int) -> Unit,
    onGetStartedClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    var offsetX by remember {
        mutableFloatStateOf(0f)
    }

    ConstraintLayout(
        modifier =
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .draggable(
                orientation = Orientation.Horizontal,
                state = DraggableState { delta ->
                    offsetX += delta
                },
                onDragStopped = {
                    // Make sure they really swipe
                    if (offsetX <= -120f && selectedIndex < maxScreens - 1) {
                        onSwipe(selectedIndex + 1)
                    }
                    if (offsetX >= 120 && selectedIndex > 0) {
                        onSwipe(selectedIndex - 1)
                    }
                    offsetX = 0f
                }
            )
    ) {
        val (getStartedContent, getStartedButtonOrSlidingDots) = createRefs()
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .constrainAs(getStartedContent) {
                    bottom.linkTo(getStartedButtonOrSlidingDots.top)
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .offset {
                    if ((offsetX <= -120f && selectedIndex < maxScreens - 1) || (offsetX >= 120 && selectedIndex > 0))
                        IntOffset(offsetX.roundToInt(), 0)
                    else
                        IntOffset(0, 0)
                }
        ) {
            if (logo != null) {
                Image(
                    painter = painterResource(id = logo),
                    contentDescription = stringResource(id = R.string.logo),
                    modifier = Modifier.size(64.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(spacing.spaceLarge))
            }
            ImageWithLegend(
                title = if (title != null) stringResource(id = title) else null,
                description = stringResource(id = description),
                painter = painter,
            )
        }

        if (selectedIndex != maxScreens - 1) {
            SlidingDots(
                modifier = Modifier
                    .padding(spacing.spaceLarge)
                    .constrainAs(getStartedButtonOrSlidingDots) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                selectedIndex = selectedIndex
            )
        } else {
            DefaultButton(
                text = stringResource(R.string.get_started),
                modifier = Modifier
                    .padding(spacing.spaceLarge)
                    .constrainAs(getStartedButtonOrSlidingDots) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .width(painter.intrinsicSize.width.dp),
                onClick = onGetStartedClick
            )
        }
    }
}

@Preview
@Composable
fun OnBoardPrev() {
    BookMeetingRoomTheme {
        OnBoard(
            onSwipe = {},
            onGetStartedClick = {},
            selectedIndex = 1,
            logo = R.drawable.claudy,
            painter = painterResource(
                id = R.drawable.claudy
            ),
            description = R.string.lorem,
            title = R.string.book_or_reserve_a_room
        )
    }
}

/**
 * Number of onBoarding screens
 */
const val maxScreens = 4

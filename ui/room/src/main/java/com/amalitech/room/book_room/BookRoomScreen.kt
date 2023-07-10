package com.amalitech.room.book_room

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.NoRippleTheme
import com.amalitech.core_ui.util.UiState
import com.amalitech.core_ui.util.formatTime
import com.amalitech.ui.room.R
import com.example.room.book_room.components.AttendeeItem
import com.example.room.book_room.components.BookRoomTitle
import com.example.room.book_room.components.FeatureItem
import com.example.room.book_room.components.SelectDateBox
import com.example.room.book_room.components.TimeSelector
import com.example.room.book_room.util.NavArguments
import com.example.room.book_room.util.formatDate
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun BookRoomScreen(
    viewModel: BookRoomViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry,
    onNavigate: () -> Unit
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val arguments = navBackStackEntry.arguments
    val roomId = arguments?.getString(NavArguments.roomId)
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current
    var roomUi: RoomUiState? by remember {
        mutableStateOf(null)
    }
    val spacing = LocalSpacing.current
    val userInput by viewModel.userInput
    val slotSelectionManager by viewModel.slotManager
    val canShowEndTimes = slotSelectionManager.canShowEndTimes
    val canShowStartTimes = slotSelectionManager.canShowStartTimes
    val availableStartTime = slotSelectionManager.availableStartTimes
    val availableEndTime = slotSelectionManager.availableEndTimes

    LaunchedEffect(key1 = true) {
        viewModel.getBookableRoom(roomId ?: "")
    }

    LaunchedEffect(key1 = uiState) {
        Log.d("uiState", "uiState = $uiState, room: $roomUi")
        when (uiState) {
            is UiState.Success -> {
                roomUi = (uiState as UiState.Success<RoomUiState>).data
                if (roomUi?.canNavigate == true)
                    onNavigate()
            }

            is UiState.Error -> {
                (uiState as UiState.Error<RoomUiState>).error?.let {
                    snackbarHostState.showSnackbar(
                        it.asString(context)
                    )
                    viewModel.onSnackBarShown()
                }
            }

            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            roomUi?.let { room ->
                if (!canShowEndTimes && !canShowStartTimes) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        AsyncImage(
                            model = room.imgUrl,
                            contentDescription = room.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(spacing.spaceExtraSmall)),
                            error = painterResource(id = R.drawable.baseline_broken_image_24),
                            placeholder = painterResource(id = R.drawable.baseline_refresh_24),
                            contentScale = ContentScale.Crop
                        )
                        Text(text = room.description)
                        SlotSelectionSection(
                            viewModel = viewModel,
                            roomUiState = room,
                            userInput = userInput,
                            onSelectEndTimeClick = {
                                viewModel.onShowEndTimeRequest(room)
                            },
                            onSelectStartTimeClick = {
                                viewModel.onShowStartTimesRequest(room)
                            }
                        )
                        Divider(modifier = Modifier.padding(vertical = spacing.spaceMedium))
                        FeatureSection(
                            roomUiState = room
                        )
                        Divider(modifier = Modifier.padding(vertical = spacing.spaceMedium))
                        AttendeesSection(
                            viewModel = viewModel,
                            userInput = userInput
                        )
                        Divider(modifier = Modifier.padding(vertical = spacing.spaceMedium))
                        NoteSection(
                            viewModel = viewModel,
                            userInput = userInput
                        )
                        Spacer(Modifier.height(spacing.spaceMedium))
                        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RectangleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                onClick = { viewModel.onBook(roomId ?: "") }
                            ) {
                                Text(
                                    text = stringResource(id = R.string.book),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                }
                if (canShowStartTimes) {
                    TimeSelector(
                        availableTimes = availableStartTime,
                        onDismiss = { viewModel.onStopShowingStartTime() },
                        selectedTime = userInput.startTime,
                        onTimeSelected = { viewModel.onStartTimeSelected(it) },
                        selectedDate = userInput.date
                    )
                }
                if (canShowEndTimes) {
                    TimeSelector(
                        availableTimes = availableEndTime,
                        onDismiss = { viewModel.onStopShowingEndTime() },
                        selectedTime = userInput.endTime,
                        onTimeSelected = { viewModel.onEndTimeSelected(it) },
                        selectedDate = userInput.date
                    )

                }
            }
            if (uiState is UiState.Loading)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun SlotSelectionSection(
    viewModel: BookRoomViewModel,
    roomUiState: RoomUiState,
    userInput: BookRoomUserInput,
    onSelectStartTimeClick: (isSelecting: Boolean) -> Unit,
    onSelectEndTimeClick: (isSelecting: Boolean) -> Unit,
) {
    val spacing = LocalSpacing.current
    val dateDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = stringResource(id = R.string.ok))
            negativeButton(text = stringResource(id = R.string.cancel))
        }
    ) {
        datepicker(
            title = stringResource(id = R.string.pick_date),
            allowedDateValidator = { date ->
                viewModel.isDateAvailable(
                    date,
                    roomUiState
                ) && LocalDate.now() <= date
            },
            initialDate = userInput.date ?: LocalDate.now()
        ) { date ->
            viewModel.onSelectedDate(date)
        }
    }
    Column(
        modifier = Modifier
    ) {
        val date = if (userInput.date != null) formatDate(userInput.date) else ""
        val startTime = if (userInput.startTime != null) formatTime(userInput.startTime) else ""
        val endTime = if (userInput.endTime != null) formatTime(userInput.endTime) else ""
        BookRoomTitle(text = stringResource(id = R.string.select_date_and_time))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.start),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { dateDialogState.show() },
                text = date,
                modifier = Modifier
                    .height(30.dp)
                    .weight(2f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { onSelectStartTimeClick(true) },
                text = startTime,
                modifier = Modifier
                    .height(30.dp)
                    .weight(2f)
            )
        }
        Spacer(Modifier.height(spacing.spaceMedium))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.end),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { dateDialogState.show() },
                text = date,
                modifier = Modifier
                    .height(30.dp)
                    .weight(2f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { onSelectEndTimeClick(true) },
                text = endTime,
                modifier = Modifier
                    .height(30.dp)
                    .weight(2f)
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureSection(
    roomUiState: RoomUiState
) {
    val spacing = LocalSpacing.current
    Column {
        BookRoomTitle(text = stringResource(id = R.string.features))
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            roomUiState.features.forEach {
                FeatureItem(feature = it)
                Spacer(Modifier.padding(spacing.spaceSmall))
            }
        }
    }
}

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AttendeesSection(
    viewModel: BookRoomViewModel,
    userInput: BookRoomUserInput
) {
    val spacing = LocalSpacing.current
    var shouldExpandList by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxWidth()) {
        BookRoomTitle(text = stringResource(id = R.string.attendees))
        Spacer(Modifier.height(spacing.spaceMedium))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = userInput.attendee,
                onValueChange = {
                    viewModel.onAttendeeNewValue(it)
                },
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
                    .padding(1.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(spacing.spaceExtraSmall)
                    ),
                shape = RoundedCornerShape(spacing.spaceExtraSmall),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                placeholder = {
                    Text(stringResource(id = com.amalitech.core.R.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.onAddAttendee()
                    keyboardController?.hide()
                })
            )
            Spacer(Modifier.width(spacing.spaceExtraSmall))
            IconButton(onClick = {
                viewModel.onAddAttendee()
                keyboardController?.hide()
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = stringResource(id = R.string.add_attendee)
                )
            }
        }

        Spacer(Modifier.height(spacing.spaceMedium))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { shouldExpandList = !shouldExpandList },
        ) {
            if (shouldExpandList) {
                userInput.attendees.forEach { attendee ->
                    AttendeeItem(
                        attendee = attendee,
                        modifier = Modifier
                            .height(30.dp)
                            .padding(spacing.spaceExtraSmall)
                    ) {
                        viewModel.onDeleteAttendee(attendee)
                    }
                }
            } else {
                for (i in 0 until userInput.attendees.size) {
                    if (i <= 2) {
                        val attendee = userInput.attendees[i]
                        AttendeeItem(
                            attendee = attendee,
                            modifier = Modifier
                                .height(30.dp)
                                .padding(spacing.spaceExtraSmall)
                        ) {
                            viewModel.onDeleteAttendee(attendee)
                        }
                    } else {
                        Text(
                            text = stringResource(
                                id = R.string.x_more,
                                userInput.attendees.size - i
                            )
                        )
                        break
                    }
                }
            }
        }
    }
}

@Composable
fun NoteSection(
    viewModel: BookRoomViewModel,
    userInput: BookRoomUserInput
) {
    val spacing = LocalSpacing.current
    BookRoomTitle(text = stringResource(id = R.string.note))
    Spacer(Modifier.height(spacing.spaceMedium))
    TextField(
        value = userInput.note,
        onValueChange = {
            viewModel.onNoteValueChanged(it)
        },
        singleLine = false,
        minLines = 3,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                shape = RoundedCornerShape(spacing.spaceExtraSmall)
            ),
        shape = RoundedCornerShape(spacing.spaceExtraSmall),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

package com.amalitech.rooms.book_room

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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.amalitech.core_ui.util.formatTime
import com.amalitech.core_ui.util.longToLocalDate
import com.amalitech.rooms.book_room.components.AttendeeItem
import com.amalitech.rooms.book_room.components.BookRoomTitle
import com.amalitech.rooms.book_room.components.FeatureItem
import com.amalitech.rooms.book_room.components.SelectDateBox
import com.amalitech.rooms.book_room.components.TimeSelector
import com.amalitech.rooms.book_room.util.NavArguments
import com.amalitech.rooms.book_room.util.formatDate
import com.amalitech.ui.rooms.R
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun BookRoomScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: BookRoomViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry,
    onNavigate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val arguments = navBackStackEntry.arguments
    val roomId = arguments?.getString(NavArguments.roomId)
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val userInput by viewModel.userInput
    val slotSelectionManager by viewModel.slotManager
    val canShowEndTimes = slotSelectionManager.canShowEndTimes
    val canShowStartTimes = slotSelectionManager.canShowStartTimes
    val availableStartTime = slotSelectionManager.availableStartTimes
    val availableEndTime = slotSelectionManager.availableEndTimes

    LaunchedEffect(key1 = true) {
        viewModel.getRoom(roomId ?: "")
    }

    LaunchedEffect(key1 = uiState) {
        if (uiState.bookRoomUi.canNavigate) {
            onNavigate()
        }
        if (uiState.error != null) {
            uiState.error?.let {
                snackbarHostState.showSnackbar(
                    it.asString(context)
                )
                viewModel.onClearError()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        uiState.bookRoomUi.let { room ->
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
                        contentScale = ContentScale.FillWidth
                    )
                    Column(modifier = Modifier.padding(spacing.spaceMedium)) {
                        Text(
                            text = stringResource(
                                id = R.string.meeting_room_for_x_people,
                                room.capacity
                            )
                        )
                        Spacer(Modifier.height(spacing.spaceMedium))
                        SlotSelectionSection(
                            viewModel = viewModel,
                            userInput = userInput,
                            onSelectStartTimeClick = {
                                viewModel.onShowStartTimesRequest()
                            }
                        ) {
                            viewModel.onShowEndTimeRequest()
                        }
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
                            Box(Modifier
                                .fillMaxWidth()) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = spacing.spaceExtraLarge)
                                        .clip(RoundedCornerShape(spacing.spaceSmall))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .align(Alignment.Center),
                                    onClick = { viewModel.onBook(roomId ?: "") },
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.book),
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }
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
        if (uiState.isLoading)
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlotSelectionSection(
    viewModel: BookRoomViewModel,
    userInput: BookRoomUserInput,
    onSelectStartTimeClick: (isSelecting: Boolean) -> Unit,
    onSelectEndTimeClick: (isSelecting: Boolean) -> Unit,
) {
    val spacing = LocalSpacing.current

    val openDialog = remember { mutableStateOf(false) }
    val state = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val localDate = longToLocalDate(utcTimeMillis)
                return viewModel.isDateAvailable(localDate) && (LocalDate.now()
                    .isBefore(localDate) || localDate.isEqual(LocalDate.now()))
            }
        }
    )

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    state.selectedDateMillis?.let { timestamp ->
                        val selectedDate = longToLocalDate(timestamp)
                        viewModel.onSelectedDate(selectedDate)
                    }
                }) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                headlineContentColor = MaterialTheme.colorScheme.onBackground,
                selectedDayContainerColor = MaterialTheme.colorScheme.background,
                selectedYearContainerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            DatePicker(
                state = state,
                colors = DatePickerDefaults.colors(
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    headlineContentColor = MaterialTheme.colorScheme.onBackground,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                    navigationContentColor = MaterialTheme.colorScheme.onBackground,
                    selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
                    selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                    yearContentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    }
    Column(
        modifier = Modifier
    ) {
        val date = if (userInput.date != null) formatDate(userInput.date) else ""
        val startTime = if (userInput.startTime != null) formatTime(userInput.startTime) else ""
        val endTime = if (userInput.endTime != null) formatTime(userInput.endTime) else ""
        BookRoomTitle(text = stringResource(id = R.string.select_date_and_time))
        Spacer(Modifier.height(spacing.spaceLarge))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = spacing.spaceLarge)
        ) {
            Text(
                text = stringResource(R.string.start),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { openDialog.value = true },
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
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = spacing.spaceLarge)
        ) {
            Text(
                text = stringResource(R.string.end),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(spacing.spaceSmall))
            SelectDateBox(
                onClick = { openDialog.value = true },
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
    Spacer(Modifier.height(spacing.spaceLarge))
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureSection(
    roomUiState: BookRoomUi
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
        Spacer(Modifier.height(spacing.spaceLarge))
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

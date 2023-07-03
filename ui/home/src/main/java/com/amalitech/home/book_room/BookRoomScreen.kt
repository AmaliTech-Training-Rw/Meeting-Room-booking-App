package com.amalitech.home.book_room

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.book_room.components.FeatureItem
import com.amalitech.home.book_room.components.SelectDateBox
import com.amalitech.home.book_room.util.NavArguments
import com.amalitech.home.book_room.util.formatDate
import com.amalitech.home.book_room.util.longToLocalDate
import com.amalitech.home.calendar.util.formatTime
import com.amalitech.ui.home.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year

@Composable
fun BookRoomScreen(
    viewModel: BookRoomViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry
) {
    val uiState by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput
    val arguments = navBackStackEntry.arguments
    val roomId by rememberSaveable {
        mutableStateOf(arguments?.getString(NavArguments.roomId))
    }
    val key1 = rememberSaveable {
        mutableStateOf(true)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var roomUi: RoomUi? = null
    val lifeCycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val updatedConfiguration = rememberSaveable { configuration }

    LaunchedEffect(key1 = updatedConfiguration) {
        viewModel.getBookableRoom(roomId ?: "")
    }

//    DisposableEffect(lifeCycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_START) {
//                viewModel.getBookableRoom(roomId ?: "")
//            } else if (event == Lifecycle.Event.ON_STOP) {
//
//            }
//        }
//    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Error -> {
                snackbarHostState.showSnackbar(
                    (uiState as UiState.Error<RoomUi>).error!!.asString(
                        context
                    )
                )
            }

            is UiState.Success -> {
                roomUi = (uiState as UiState.Success<RoomUi>).data
            }
            else -> {}
        }
    }

    SlotSelectionSection(
        viewModel = viewModel,
        roomUi
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SlotSelectionSection(
    viewModel: BookRoomViewModel,
    roomUi: RoomUi?
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput
    var isSelectingDate by rememberSaveable {
        mutableStateOf(false)
    }
    var isSelectingHour by rememberSaveable {
        mutableStateOf(false)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (uiState) {
            is UiState.Success -> {
                (uiState as UiState.Success<RoomUi>).data?.let { roomUi ->
                    val startDateState = rememberDatePickerState(
                        selectableDates = object : SelectableDates {
                            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                                val date = longToLocalDate(utcTimeMillis)
                                return viewModel.isDateAvailable(
                                    date,
                                    roomUi
                                ) && LocalDate.now() <= date
                            }

                            override fun isSelectableYear(year: Int): Boolean {
                                return year >= Year.now().value
                            }
                        },
                        initialSelectedDateMillis = System.currentTimeMillis()
                    )
                    if (isSelectingDate) {
                        DatePickerDialog(onDismissRequest = {
                            isSelectingDate = false
                        }, confirmButton = {
                            TextButton(onClick = {
                                if (startDateState.selectedDateMillis != null) {
                                    isSelectingDate = false
                                    viewModel.onSelectedDate(longToLocalDate(startDateState.selectedDateMillis!!))
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            UiText.StringResource(R.string.error_no_date_selected)
                                                .asString(context)
                                        )
                                    }
                                }
                            }) {
                                Text(stringResource(id = R.string.ok))
                            }
                        },
                            dismissButton = {
                                TextButton(onClick = {
                                    isSelectingDate = false
                                }) {
                                    Text(stringResource(id = R.string.cancel))
                                }
                            }
                        ) {
                            DatePicker(
                                state = startDateState,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> {}
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val date = if (userInput.date != null) formatDate(userInput.date!!) else ""
            val startTime = if (userInput.startTime != null) formatTime(
                LocalDateTime.of(
                    LocalDate.now(),
                    userInput.startTime
                )
            ) else ""
            val spacing = LocalSpacing.current
            Text(
                text = stringResource(R.string.select_time),
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.start),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(spacing.spaceSmall))
                SelectDateBox(
                    onClick = { isSelectingDate = !isSelectingDate },
                    text = date,
                    modifier = Modifier
                        .height(30.dp)
                        .weight(0.5f)
                )
                Spacer(Modifier.width(spacing.spaceSmall))
                SelectDateBox(
                    onClick = { isSelectingHour = !isSelectingHour },
                    text = startTime,
                    modifier = Modifier
                        .height(30.dp)
                        .weight(0.5f)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureSection(roomUi: RoomUi) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        roomUi.features.forEach {
            FeatureItem(feature = it)
        }
    }
}

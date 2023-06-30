package com.amalitech.home.book_room

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.UiState
import com.amalitech.home.book_room.util.NavArguments
import com.amalitech.home.book_room.util.formatDate
import com.amalitech.home.book_room.util.longToLocalDate
import com.amalitech.ui.home.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.Year

@Composable
fun BookRoomScreen(
    viewModel: BookRoomViewModel = koinViewModel(),
    navBackStackEntry: NavBackStackEntry
) {
    val uiState by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput
    val arguments = navBackStackEntry.arguments
    val roomId = arguments?.getString(NavArguments.roomId)


    LaunchedEffect(key1 = true) {
        viewModel.getBookableRoom(roomId ?: "")
    }

    SlotSelectionSection(
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SlotSelectionSection(
    viewModel: BookRoomViewModel
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val uiState by viewModel.publicBaseResult.collectAsStateWithLifecycle()
    val userInput by viewModel.userInput
    val focusRequester = remember {
        FocusRequester()
    }
    var isSelectingDate by rememberSaveable {
        mutableStateOf(false)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        LaunchedEffect(key1 = uiState) {
            when (uiState) {
                is UiState.Error -> {
                    snackbarHostState.showSnackbar(
                        (uiState as UiState.Error<RoomUi>).error!!.asString(
                            context
                        )
                    )
                }
                else -> {}
            }
        }
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
                            focusRequester.freeFocus()
                        }, confirmButton = {
                            TextButton(onClick = {
                                if (startDateState.selectedDateMillis != null) {
                                    isSelectingDate = false
                                    focusRequester.freeFocus()
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
                                    focusRequester.freeFocus()
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
                CircularProgressIndicator()
            }
            else -> {}
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val date = if (userInput.date != null) formatDate(userInput.date!!) else ""
            TextField(
                value = date,
                onValueChange = {},
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isSelectingDate = it.isFocused
                    }
            )
        }
    }
}

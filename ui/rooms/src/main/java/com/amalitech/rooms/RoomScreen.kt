package com.amalitech.rooms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.UiState
import com.amalitech.rooms.components.DialogButton
import com.amalitech.rooms.components.RoomCard
import com.amalitech.ui.rooms.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListScreen(
    viewModel: RoomViewModel = koinViewModel(),
    onNavigateToAddRoom: (room: Room) -> Unit
) {
    val spacing = LocalSpacing.current
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    var selectedRoom: Room? by remember {
        mutableStateOf(null)
    }
    var rooms: List<Room>? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is UiState.Error -> (uiState as UiState.Error<List<Room>>).error?.let {
                snackbarHostState.showSnackbar(
                    it.asString(context)
                )
            }

            is UiState.Success -> rooms = (uiState as UiState.Success).data

            else -> {}
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        when (uiState) {
            is UiState.Success -> LazyColumn(
                contentPadding = PaddingValues(vertical = spacing.spaceMedium),
                verticalArrangement = Arrangement.spacedBy(spacing.spaceMedium)
            ) {
                rooms?.let { rooms ->
                    items(rooms) { room ->
                        RoomCard(
                            room = room,
                            modifier = Modifier.height(150.dp),
                            onLeftContentClick = { onNavigateToAddRoom(room) },
                            onRightContentClick = {
                                openDialog = true
                                selectedRoom = room
                            }
                        )
                    }
                }
            }

            is UiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
            else -> {}
        }
        if (openDialog && selectedRoom != null) {
            AlertDialog(
                onDismissRequest = { openDialog = false },
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(spacing.spaceMedium),
                    tonalElevation = AlertDialogDefaults.TonalElevation,
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = stringResource(id = R.string.question_delete_room, selectedRoom!!.roomName)
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        Row(Modifier.align(Alignment.End)) {
                            DialogButton(onClick = {
                                openDialog = false
                            })
                            Spacer(Modifier.width(spacing.spaceMedium))
                            DialogButton(
                                onClick = {
                                    viewModel.deleteRoom(selectedRoom!!)
                                    openDialog = false
                                },
                                text = stringResource(R.string.delete),
                                backgroundColor = MaterialTheme.colorScheme.error,
                                textColor = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }
            }
        }
    }
    // TODO("ADD FLOATING ACTION BUTTON ONCE ICONS CAN BE ADDED DYNAMICALLY")
}

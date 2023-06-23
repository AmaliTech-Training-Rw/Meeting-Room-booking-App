package com.amalitech.admin.room

import android.util.Log
import android.view.KeyEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.R
import com.amalitech.core_ui.components.BookMeetingRoomDropDown
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.SnackbarManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRoomScreen(
    viewModel: AddRoomViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var isDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val focusManager: FocusManager = LocalFocusManager.current
    var roomLocations: List<String> by rememberSaveable {
        mutableStateOf(listOf())
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (add_photos, photo_selector, images, form) = createRefs()

            Text(
                text = stringResource(com.amalitech.core.R.string.add_photos),
                modifier = Modifier
                    .constrainAs(add_photos) {
                        top.linkTo(parent.top, 48.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 8.dp)
                        width = Dimension.fillToConstraints
                    },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .constrainAs(photo_selector) {
                        top.linkTo(add_photos.bottom, 33.dp)
                        start.linkTo(add_photos.start)
                        width = Dimension.wrapContent
                    }
                    .size(85.dp, 66.dp)
                    .border(
                        BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outline
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(14.dp, 12.dp, 14.dp, 12.dp)
            ) {
                IconButton(
                    onClick = { /* ... */ },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(57.dp, 42.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
            }

            val list = listOf(
                "A", "B", "C", "D"
            ) + ((0..100).map { it.toString() })
            LazyRow(
                modifier = Modifier
                    .constrainAs(images) {
                        top.linkTo(add_photos.bottom, 33.dp)
                        start.linkTo(photo_selector.end)
                        end.linkTo(add_photos.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                items(items = list, itemContent = { item ->
                    val image: Painter = painterResource(id = R.drawable.room)
                    Image(
                        painter = image,
                        contentDescription = item,
                        modifier = Modifier
                            .size(85.dp, 66.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                })
            }

            LazyColumn(
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(photo_selector.bottom, 33.dp)
                        start.linkTo(photo_selector.start)
                        end.linkTo(images.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxSize()
            ) {
                item {
                    RoomTextField(
                        placeholder = stringResource(com.amalitech.core.R.string.add_room),
                        value = state.name,
                        onValueChange = {
                            viewModel.onRoomName(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Password
                        ),
                        onGo = {
                            viewModel.onSaveRoomClick()
                        }
                    )
                }

                item {
                    Text(
                        text = stringResource(com.amalitech.core.R.string.room_capacity),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }

                item {
                    RoomCounter(
                        state.capacity,
                        viewModel::onRemoveRoomCapacity,
                        viewModel::onAddRoomCapacity
                    )
                }

                item {
                    BookMeetingRoomDropDown(
                        isDropDownExpanded = isDropDownExpanded,
                        items = roomLocations,
                        onSelectedItemChange = {
                            viewModel.onSelectedLocation(it)
                        },
                        onIsExpandedStateChange = { isDropDownExpanded = it },
                        selectedItem = state.location,
                        focusManager = focusManager,
                        com.amalitech.core.R.string.select_location,
                    ) { isDropDownExpanded = it }
                }
            }
        }
    }
}

@Composable
fun RoomCounter(
    value: Int = 1,
    removeRoom: () -> Unit,
    addRoom: () -> Unit
) {
    Box(
        Modifier
            .border(
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(5.dp)
            )
            .size(460.dp, 39.dp)
            .padding(16.dp, 0.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Remove,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    onClick = {
                        if (value > 1) {
                            removeRoom()
                        }
                    }
                )
                .align(Alignment.CenterStart)
                .size(20.dp)
        )

        Text(
            text = value.toString(),
            modifier = Modifier
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )

        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    onClick = {
                        addRoom()
                    }
                )
                .align(Alignment.CenterEnd)
                .size(20.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RoomTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    placeholderTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Password
    ),
    focusManager: FocusManager = LocalFocusManager.current,
    onGo: () -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = { focusManager.moveFocus(FocusDirection.Down) },
        onDone = { focusManager.clearFocus() },
        onGo = { onGo() }
    )
) {
    val spacing = LocalSpacing.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        modifier = modifier
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                shape = RoundedCornerShape(spacing.spaceExtraSmall)
            )
            .padding(spacing.spaceExtraSmall)
            .onPreviewKeyEvent {
                if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else if (it.key == Key.Enter) {
                    onGo()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(spacing.spaceExtraSmall),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                style = placeholderTextStyle
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview
@Composable
fun RoomTextFieldPreview() {
    BookMeetingRoomTheme {
        var value by rememberSaveable {
            mutableStateOf("")
        }
        RoomTextField(
            placeholder = "password",
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            focusManager = LocalFocusManager.current
        )
    }
}

@Composable
fun AddRoomScreenPreview() {
    BookMeetingRoomTheme {
        AddRoomScreen()
    }
}

@Composable
fun RoomCounterPreview() {
    BookMeetingRoomTheme {
        RoomCounter(
            1,
            {},
            {}
        )
    }
}

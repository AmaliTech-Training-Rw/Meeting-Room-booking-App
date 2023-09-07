package com.amalitech.rooms

import android.Manifest
import android.os.Build
import android.view.KeyEvent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import coil.compose.rememberAsyncImagePainter
import com.amalitech.core.R
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.BookMeetingRoomDropDown
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.add_room_icon_button_bg
import com.amalitech.core_ui.util.requestImagePermission
import com.amalitech.rooms.components.DialogButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(
    appState: BookMeetingRoomAppState,
    viewModel: AddRoomViewModel = koinViewModel(),
    onComposing: (AppBarState) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var isDropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val focusManager: FocusManager = LocalFocusManager.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {
            viewModel.onRoomImages(it)
        }
    val spacing = LocalSpacing.current
    val title = stringResource(id = R.string.add_room)
    val contentDescription = stringResource(id = com.amalitech.core_ui.R.string.navigate_back)
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
    var shouldShowRational by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldShowOpenSettings by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                title = title,
                navigationIcon = {
                    NavigationButton(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = contentDescription
                    ) {
                        onNavigateBack()
                    }
                }
            )
        )
    }
    LaunchedEffect(key1 = state) {
        val snackbar = state.error
        if (snackbar != null) {
            appState.snackbarHostState.showSnackbar(snackbar.asString(context))
            viewModel.clearSnackBar()
        }
        if (state.canNavigate)
            onNavigateBack()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if(shouldShowRational) {
            AlertDialog(
                onDismissRequest = { shouldShowRational = false },
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
                            text = stringResource(
                                id = com.amalitech.ui.rooms.R.string.permission_is_needed,
                            )
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        Row(Modifier.align(Alignment.End)) {
                            DialogButton(onClick = {
                                shouldShowRational = false
                            })
                        }
                    }
                }
            }
        }
        if (shouldShowOpenSettings) {
            AlertDialog(
                onDismissRequest = { shouldShowOpenSettings = false },
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
                            text = stringResource(
                                id = com.amalitech.ui.rooms.R.string.permission_is_needed_open_settings,
                            )
                        )
                        Spacer(modifier = Modifier.height(spacing.spaceMedium))

                        Row(Modifier.align(Alignment.End)) {
                            DialogButton(onClick = {
                                shouldShowOpenSettings = false
                            })
                        }
                    }
                }
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (addPhotos, photoSelector, images, form) = createRefs()

            Text(
                text = stringResource(R.string.add_photos),
                modifier = Modifier
                    .constrainAs(addPhotos) {
                        top.linkTo(parent.top, 16.dp) // set to 48.dp
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 8.dp)
                        width = Dimension.fillToConstraints
                    },
                color = MaterialTheme.colorScheme.outlineVariant,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )

            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .constrainAs(photoSelector) {
                        top.linkTo(addPhotos.bottom, 33.dp)
                        start.linkTo(addPhotos.start)
                        width = Dimension.wrapContent
                    }
                    .size(85.dp, 66.dp)
                    .border(
                        BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        spacing.spaceMedium,
                        spacing.spaceSmall,
                        spacing.spaceMedium,
                        spacing.spaceSmall
                    )
            ) {
                IconButton(
                    onClick = {
                        requestImagePermission(
                            permissionState = permissionState,
                            onShouldShowRational = {
                                shouldShowRational = it
                            }
                        ) {
                            shouldShowOpenSettings = it
                        }
                        if (permissionState.allPermissionsGranted) {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(57.dp, 42.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(add_room_icon_button_bg.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(
                            id = R.string.add_room_button
                        ),
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .constrainAs(images) {
                        top.linkTo(addPhotos.bottom, 33.dp)
                        start.linkTo(photoSelector.end)
                        end.linkTo(addPhotos.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                if (state.imagesList.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.select_photos),
                            modifier = Modifier
                                .constrainAs(addPhotos) {
                                    top.linkTo(parent.top, spacing.spaceMedium) // set to 48.dp
                                    start.linkTo(parent.start, spacing.spaceMedium)
                                    end.linkTo(parent.end, spacing.spaceSmall)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(spacing.spaceMedium),
                            color = MaterialTheme.colorScheme.outlineVariant,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Light,
                            fontSize = 16.sp
                        )
                    }
                }
                items(
                    items = state.imagesList,
                    itemContent = { uri ->
                        ConstraintLayout(
                            modifier = Modifier
                                .wrapContentSize()
                        ) {
                            val (pic, icon) = createRefs()

                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentScale = ContentScale.FillWidth,
                                contentDescription = stringResource(
                                    id = R.string.features_empty
                                ),
                                modifier = Modifier
                                    .size(85.dp, 66.dp)
                                    .clip(RoundedCornerShape(spacing.spaceExtraSmall))
                                    .padding(spacing.spaceExtraSmall)
                                    .constrainAs(pic) {
                                        top.linkTo(parent.top)
                                        end.linkTo(parent.end)
                                    }
                            )
                            IconButton(
                                onClick = { viewModel.onDeleteImage(uri) },
                                modifier = Modifier
                                    .constrainAs(icon) {
                                        top.linkTo(pic.top)
                                        end.linkTo(pic.end)
                                    }
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Icon(
                                    Icons.Default.Clear,
                                    "",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    })

            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                contentPadding = PaddingValues(
                    vertical = spacing.spaceSmall
                ),
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(photoSelector.bottom, 33.dp)
                        start.linkTo(photoSelector.start)
                        end.linkTo(images.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxSize()
            ) {
                item {
                    RoomTextField(
                        placeholder = stringResource(R.string.add_room),
                        value = state.name,
                        onValueChange = {
                            viewModel.onRoomName(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Text
                        ),
                        onGo = {
                            viewModel.onSaveRoomClick(context)
                        },
                        hasError = state.error != null
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.room_capacity),
                        color = MaterialTheme.colorScheme.outlineVariant,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light,
                        fontSize = 16.sp
                    )
                }

                item {
                    RoomCounter(
                        state.capacity,
                        viewModel::onRemoveRoomCapacity,
                        viewModel::onAddRoomCapacity,
                        viewModel::onNewRoomCapacity
                    )
                }

                item {
                    BookMeetingRoomDropDown(
                        isDropDownExpanded = isDropDownExpanded,
                        items = state.locationList?.map { it.name } ?: emptyList(),
                        onSelectedItemChange = {
                            viewModel.onSelectedLocation(it)
                        },
                        onIsExpandedStateChange = { isDropDownExpanded = it },
                        selectedItem = state.locationList?.find { it.id == state.location }?.name ?: ""
                        ,
                        focusManager = focusManager,
                        R.string.select_location,
                    ) { isDropDownExpanded = it }
                }

                item {
                    RoomTextField(
                        placeholder = stringResource(R.string.add_features),
                        value = state.features,
                        onValueChange = {
                            viewModel.onFeatures(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(118.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Text
                        ),
                        onGo = {
                            viewModel.onSaveRoomClick(context)
                        },
                        singleLine = false
                    )
                }

                item {
                    DefaultButton(
                        text = stringResource(R.string.save_room),
                        onClick = {
                            viewModel.onSaveRoomClick(context)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = false // TODO: use network ui state here
                    )
                }
            }
        }
    }
}

@Composable
fun RoomCounter(
    value: Int = 1,
    removeRoom: () -> Unit,
    addRoom: () -> Unit,
    onNewValue: (Int) -> Unit
) {
    val spacing = LocalSpacing.current
    val pattern = remember { Regex("^\\d+\$") }

    TextField(
        value = value.toString(),
        onValueChange = {
            if (it.isEmpty() || it.matches(pattern)) {
                onNewValue(it.toInt())
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(
                    id = R.string.add_room_counter
                ),
                modifier = Modifier
                    .clickable(
                        onClick = {
                            addRoom()
                        }
                    )
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = stringResource(
                    id = R.string.add_room_counter
                ),
                modifier = Modifier
                    .clickable(
                        onClick = {
                            if (value > 1) {
                                removeRoom()
                            }
                        }
                    )
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                shape = RoundedCornerShape(spacing.spaceExtraSmall)
            )
            .padding(spacing.spaceExtraSmall),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}

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
        keyboardType = KeyboardType.Text
    ),
    focusManager: FocusManager = LocalFocusManager.current,
    onGo: () -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions(
        onNext = { focusManager.moveFocus(FocusDirection.Down) },
        onDone = { focusManager.clearFocus() },
        onGo = { onGo() }
    ),
    singleLine: Boolean = true,
    hasError: Boolean = false
) {
    val spacing = LocalSpacing.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = singleLine,
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
        isError = hasError,
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
            placeholder = stringResource(R.string.add_room),
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            focusManager = LocalFocusManager.current
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
fun AddRoomScreenPreview() {
    BookMeetingRoomTheme {
        AddRoomScreen(onComposing = {}, appState = rememberBookMeetingRoomAppState()) {}
    }
}

@Preview
@Composable
fun RoomCounterPreview() {
    BookMeetingRoomTheme {
        RoomCounter(
            1,
            {},
            {},
            {}
        )
    }
}

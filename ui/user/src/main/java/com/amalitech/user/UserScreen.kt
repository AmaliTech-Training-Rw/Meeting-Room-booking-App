package com.amalitech.user

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.components.AppBarState
import com.amalitech.core_ui.components.DefaultButton
import com.amalitech.core_ui.components.NavigationButton
import com.amalitech.core_ui.components.PainterActionButton
import com.amalitech.core_ui.components.SearchIcon
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.swipe_animation.SwipeAction
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.swipe_animation.util.SwipeDirection
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.Dimensions
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.theme.add_user_divider
import com.amalitech.core_ui.util.CustomBackHandler
import com.amalitech.ui.user.R
import com.amalitech.user.adduser.AddUserScreen
import com.amalitech.user.adduser.AddUserViewModel
import com.amalitech.user.models.User
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = koinViewModel(),
    addUserViewModel: AddUserViewModel = koinViewModel(),
    appState: BookMeetingRoomAppState,
    onOpenDrawer: () -> Unit,
    navigateToProfileScreen: () -> Unit,
    navigateUp: () -> Unit,
    onComposing: (AppBarState) -> Unit
) {
    val spacing = LocalSpacing.current
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val addUserState by addUserViewModel.userUiState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val title = stringResource(id = R.string.users)
    var isSearchQueryVisible by remember {
        mutableStateOf(false)
    }
    val appBarState = AppBarState(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.clip(CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.show_bottom_sheet)
                )
            }
        },
        title = title,
        actions = {
            SearchIcon(
                searchQuery = uiState.searchQuery,
                onSearch = { viewModel.onSearch() },
                onSearchQueryChange = viewModel::onNewSearchQuery,
                isSearchTextFieldVisible = isSearchQueryVisible,
                onSearchTextFieldVisibilityChanged = {
                    isSearchQueryVisible = it
                    if (!isSearchQueryVisible) {
                        viewModel.resetList()
                    }
                }
            )
            PainterActionButton {
                navigateToProfileScreen()
            }
        },
        navigationIcon = {
            NavigationButton {
                onOpenDrawer()
            }
        }
    )
    val context = LocalContext.current

    CustomBackHandler(
        appState = appState,
        onComposing = onComposing,
        navigateUp = navigateUp
    )
    LaunchedEffect(true) {
        onComposing(appBarState)
    }
    LaunchedEffect(key1 = uiState) {
        val message = uiState.snackbarMessage
        if (message != null) {
            appState.snackbarHostState.showSnackbar(message.asString(context))
            viewModel.clearMessage()
        }
    }
    val snackbarMessage by addUserViewModel.snackbarMessage

    LaunchedEffect(key1 = snackbarMessage) {
        snackbarMessage?.let {
            appState.snackbarHostState.showSnackbar(it.asString(context))
            addUserViewModel.clearSnackBar()
        }

    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                viewModel.onAddUser()
            },
            sheetState = sheetState,
            scrimColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {

                Text(
                    text = stringResource(id = R.string.invite_user),
                    modifier = Modifier.padding(
                        start = spacing.spaceMedium,
                        top = spacing.spaceMedium
                    ),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700
                    ),
                    textAlign = TextAlign.Start,
                )
                Divider(
                    color = add_user_divider,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp)
                        .padding(top = spacing.spaceExtraSmall, bottom = spacing.spaceExtraSmall)
                )

                DefaultTextField(
                    placeholder = stringResource(com.amalitech.core.R.string.first_name),
                    value = addUserState.firstName,
                    onValueChange = {
                        addUserViewModel.onFirstName(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Text
                    )
                )

                DefaultTextField(
                    placeholder = stringResource(com.amalitech.core.R.string.last_name),
                    value = addUserState.lastName,
                    onValueChange = {
                        addUserViewModel.onLastName(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Text
                    )
                )

                DefaultTextField(
                    placeholder = stringResource(com.amalitech.core.R.string.email),
                    value = addUserState.email,
                    onValueChange = {
                        addUserViewModel.onEmailName(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Text
                    )
                )

                DefaultTextField(
                    placeholder = stringResource(com.amalitech.core.R.string.location),
                    value = addUserState.selectLocation,
                    onValueChange = {
                        addUserViewModel.onLocationName(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.spaceExtraSmall),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Go,
                        keyboardType = KeyboardType.Text
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = addUserState.isAdmin,
                        onCheckedChange = { checked ->
                            addUserViewModel.onIsAdminChecked(checked)
//                            SnackbarManager.showMessage(SnackbarMessage.StringSnackbar("checked_ = $checked"))
                        },
                        modifier = Modifier
                            .padding(spacing.spaceExtraSmall),
                    )

                    Text(
                        text = "Is Admin"
                    )
                }

                Divider(
                    color = add_user_divider,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp)
                        .padding(
                            top = spacing.spaceExtraSmall,
                            bottom = spacing.default
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                ) {

                    DefaultButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(spacing.spaceExtraSmall),
                        text = stringResource(id = R.string.cancel),
                        textColor = MaterialTheme.colorScheme.onBackground,
                        backgroundColor = MaterialTheme.colorScheme.background,
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        borderWidth = 1.dp,
                        borderColor = Color.Black
                    )

                    DefaultButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(spacing.spaceExtraSmall),
                        text = stringResource(id = R.string.invite),
                        textColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = {
                            addUserViewModel.invite()
                            showBottomSheet = false
                        }
                    )
                }
            }
        }
    }

    UsersList(
        modifier,
        viewModel,
        spacing
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersList(
    modifier: Modifier,
    viewModel: UserViewModel,
    spacing: Dimensions
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog) {
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
                        text = "Would you like to delete the user?"
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    Row(Modifier.align(Alignment.End)) {
                        DialogButton(onClick = {
                            openDialog = false
                        })
                        Spacer(Modifier.width(spacing.spaceMedium))
                        DialogButton(
                            onClick = {
                                viewModel.onDelete()
                                openDialog = false
                            },
                            text = "Delete",
                            backgroundColor = MaterialTheme.colorScheme.error,
                            textColor = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
    ) {
        items(items = state.users, itemContent = { item ->
            var isLeftContentVisible by rememberSaveable {
                mutableStateOf(false)
            }

            var isRightContentVisible by rememberSaveable {
                mutableStateOf(false)
            }

            SwipeableCardSideContents(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(77.dp),
                isLeftContentVisible = isLeftContentVisible,
                isRightContentVisible = isRightContentVisible,
                onSwipeEnd = { direction ->
                    when (direction) {
                        SwipeDirection.LEFT -> {
                            if (isRightContentVisible)
                                isRightContentVisible = false
                            else
                                isLeftContentVisible = false
                        }

                        SwipeDirection.NONE -> {
                            isLeftContentVisible = false
                            isRightContentVisible = false
                        }

                        SwipeDirection.RIGHT -> {
                            if (isLeftContentVisible)
                                isLeftContentVisible = false
                            else
                                isRightContentVisible = true
                        }
                    }
                },
                rightContent = {
                    SwipeAction(
                        backgroundColor = MaterialTheme.colorScheme.error,
                        icon = Icons.Filled.Delete,
                        onActionClick = {
                            openDialog = true
                        },
                        modifier = Modifier.padding(vertical = spacing.spaceExtraSmall)
                    )
                },
                leftContent = {},
                content = {
                    UserItem(
                        isRightContentVisible,
                        item
                    )
                }
            )
        })
    }
}

@Composable
fun UserItem(
    isRightVisible: Boolean,
    user: User
) {
    val spacing = LocalSpacing.current
    val cardBg = if (isRightVisible) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.background
    }

    // TODO: is inactive an attribute coming from the server? or is it triggered by a swipe gesture?
    val activeText = if (user.isActive) {
        R.string.active
    } else {
        R.string.inactive
    }

    val activeTextBg = if (user.isActive) {
        MaterialTheme.colorScheme.tertiaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .background(cardBg)
            .padding(spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.user),
            contentDescription = stringResource(id = R.string.user_image),
            modifier = Modifier.size(45.dp),
        )

        Spacer(Modifier.width(spacing.spaceExtraSmall))

        Column(
            modifier = Modifier
                .weight(2f)
        ) {
            Text(
                text = user.username,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = user.email,
                modifier = Modifier,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center
                )
            )
        }

        // TODO: change the text and bg color when swiped
        Text(
            text = stringResource(id = activeText),
            modifier = Modifier
                .clip(RoundedCornerShape(spacing.spaceMedium))
                .padding(spacing.spaceExtraSmall)
                .clickable { },
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                background = activeTextBg
            )
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DefaultTextField(
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
    hasError: Pair<Boolean, String> = Pair(false, "")
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
        isError = hasError.first,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    BookMeetingRoomTheme {
        UserItem(
            true,
            User(
                "4",
                "cool",
                "User Name",
                "example@gmail.com",
                true
            )
        )
    }
}


@Composable
fun DialogButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.cancel),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    val spacing = LocalSpacing.current
    TextButton(
        onClick = onClick,
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(spacing.spaceMedium))
            .border(
                1.dp,
                textColor,
                RoundedCornerShape(spacing.spaceMedium)
            )
            .background(backgroundColor)
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    BookMeetingRoomTheme {
        UserScreen(
            appState = rememberBookMeetingRoomAppState(),
            onOpenDrawer = {},
            navigateToProfileScreen = {},
            navigateUp = {}
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun AddUserScreenPreview() {
    BookMeetingRoomTheme {
        AddUserScreen(
            true,
            PaddingValues(12.dp)
        )
    }
}

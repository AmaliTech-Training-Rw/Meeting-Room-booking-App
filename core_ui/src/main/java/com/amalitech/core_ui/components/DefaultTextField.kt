package com.amalitech.core_ui.components

import android.view.KeyEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.theme.LocalSpacing

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
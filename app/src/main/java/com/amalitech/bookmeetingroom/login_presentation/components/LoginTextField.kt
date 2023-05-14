package com.amalitech.bookmeetingroom.login_presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.bookmeetingroom.R
import com.amalitech.bookmeetingroom.ui.theme.BookMeetingRoomTheme
import com.amalitech.bookmeetingroom.ui.theme.LocalSpacing

@Composable
fun LoginTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    textStyle: TextStyle = TextStyle(),
    placeholderTextStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    )
) {
    val spacing = LocalSpacing.current
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        singleLine = true,
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible)
                    R.drawable.baseline_visibility_off_24
                else
                    R.drawable.baseline_visibility_24
                val description = if (passwordVisible)
                    stringResource(id = R.string.hide_password)
                else
                    stringResource(id = R.string.show_password)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = description
                    )
                }
            }
        },
        modifier = modifier
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(spacing.extraSmall)
            )
            .padding(spacing.extraSmall),
        shape = RoundedCornerShape(spacing.extraSmall),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedContainerColor = MaterialTheme.colorScheme.background
        ),
        placeholder = {
            Text(
                text = placeholder,
                style = placeholderTextStyle
            )
        }
    )
}

@Preview
@Composable
fun Prev() {
    BookMeetingRoomTheme {
        var value by rememberSaveable {
            mutableStateOf("")
        }
        LoginTextField(
            placeholder = "password",
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.fillMaxWidth(),
            isPassword = false
        )
    }
}

package com.amalitech.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.swipe_animation.SwipeableCardSideContents
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.user.R

@Composable
fun UserScreen() {
    SwipeableCardSideContents(
        modifier = Modifier
            .fillMaxWidth()
            .height(77.dp),
        leftContent = {
            Text(
                "leftContent"
            )
        },
        rightContent = {
            Text(
                "rightContent"
            )
        },
        content = {
            UserItem()
        }
    )
}

@Composable
fun UserItem() {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(spacing.spaceSmall))
            .background(MaterialTheme.colorScheme.background)
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
                .border(
                    border = BorderStroke(
                        1.dp,
                        Color.Blue
                    )
                )
                .weight(2f)) {
            Text(
                text = stringResource(id = R.string.username),
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            1.dp,
                            Color.Cyan
                        )
                    ),
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
                text = stringResource(id = R.string.email),
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            1.dp,
                            Color.Red
                        )
                    ),
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
            text = stringResource(id = R.string.active),
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        1.dp,
                        Color.Yellow
                    )
                )
                .clip(RoundedCornerShape(spacing.spaceMedium))
                .padding(spacing.spaceExtraSmall)
                .clickable {  },
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp,
                background = MaterialTheme.colorScheme.tertiaryContainer
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    BookMeetingRoomTheme {
        UserItem()
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    BookMeetingRoomTheme {
        UserScreen()
    }
}
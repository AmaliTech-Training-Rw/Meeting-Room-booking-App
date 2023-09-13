package com.amalitech.rooms.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun RoomDescription(
    room: Room,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(room.imageUrl.randomOrNull() ?: "")
                .crossfade(true)
                .build(),
            contentDescription = room.roomName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            error = painterResource(id = com.amalitech.core_ui.R.drawable.room_small),
//            placeholder = painterResource(id = R.drawable.baseline_refresh_24)
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(vertical = spacing.spaceMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(0.4f)
            ) {
                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                )
                Column(Modifier.padding(start = spacing.spaceSmall)) {
                    Text(
                        text = room.roomName,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = com.amalitech.core_ui.R.string.up_to_people, room.numberOfPeople),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.spaceSmall))
            val features = buildAnnotatedString {
                room.roomFeatures.forEachIndexed { index, string ->
                    if (index != 0) {
                        append(", ")
                        append(string)
                    } else {
                        append(string)
                    }
                }
            }
            Text(
                text = features,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = spacing.spaceSmall)
            )
        }
    }
}

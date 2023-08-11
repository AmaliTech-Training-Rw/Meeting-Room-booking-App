package com.amalitech.home.room.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amalitech.core.data.model.Room
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.ui.home.R

@Composable
fun RoomItem(
    room: Room,
    modifier: Modifier = Modifier,
    onBookRoom: (Room) -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .padding(end = spacing.spaceMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(room.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = room.roomName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(150.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(spacing.spaceMedium))
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(2f)
                .padding(vertical = spacing.spaceMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(50.dp)
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
                        text = stringResource(
                            id = com.amalitech.core_ui.R.string.up_to_people,
                            room.numberOfPeople
                        ),
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
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = spacing.spaceExtraSmall),
                maxLines = 3
            )
            Spacer(Modifier.height(spacing.spaceSmall))
            Button(
                onClick = { onBookRoom(room) },
                modifier = Modifier
                    .align(Alignment.End)
                    .wrapContentHeight()
                ,
                shape = RoundedCornerShape(spacing.spaceSmall),
                elevation = ButtonDefaults.buttonElevation(spacing.spaceExtraSmall)
            ) {
                Text(stringResource(id = R.string.book))
            }
        }
    }
}

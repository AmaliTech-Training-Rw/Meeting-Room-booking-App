package com.amalitech.home.room

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.R

@Composable
fun RoomScreen() {
    val list = listOf(
        "A", "B", "C", "D"
    ) + ((0..10).map { it.toString() })

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(0.dp, 16.dp)
    ) {
        items(items = list, itemContent = { item ->
            RoomItem(item)
        })
    }
}

@Composable
fun RoomItem(item: String) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .padding(5.dp, 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxSize()
                .wrapContentHeight()
        ) {
            val image: Painter = painterResource(id = R.drawable.room)
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier,
            )

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                RoomName()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Air conditioning, Internet, Whiteboard, Natural light, Drinks",
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.W300,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun RoomName() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Divider(
            modifier = Modifier
                .size(3.dp, 40.dp)
                .padding(0.dp, 4.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "Room Name",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W700,
                fontSize = 13.sp
            )
            Text(
                text = "Up to 6 people",
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.W300,
                fontSize = 16.sp
            )
        }
    }
}

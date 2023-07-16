package com.amalitech.home.room

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import com.amalitech.core_ui.R

@Composable
fun RoomScreen() {
    val list = listOf(
        "A", "B", "C", "D"
    ) + ((0..10).map { it.toString() })
    LazyColumn() {
        items(items = list, itemContent = { item ->
            RoomItem(item)
        })
    }
}

@Composable
fun RoomItem(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 15.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            val image: Painter = painterResource(id = R.drawable.room)
            Image(
                painter = image,
                contentDescription = item,
                modifier = Modifier
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .padding(0.dp, 15.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            RoomName()
        }
    }
}

@Composable
fun RoomName() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
//            .border(2.dp, Blue)
            .fillMaxSize()
    ) {
        val image: Painter = painterResource(id = R.drawable.line)
        Image(
            painter = image,
            contentDescription = "line",
            modifier = Modifier.fillMaxHeight()
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




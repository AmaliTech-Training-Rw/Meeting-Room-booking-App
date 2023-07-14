package com.amalitech.home.room

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.R

@Composable
fun RoomScreen() {
    val list = listOf(
        "A", "B", "C", "D"
    ) + ((0..10).map { it.toString() })
    LazyColumn(

    ) {
        items(items = list, itemContent = { item ->
            RoomItem(item)
        })
    }
}

@Composable
fun RoomItem(item: String) {
    Row(
        modifier = Modifier
            .padding(10.dp)
    ) {
        val image: Painter = painterResource(id = R.drawable.room)
        Image(
            painter = image,
            contentDescription = item,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
        )
        Column(
            Modifier.weight(2f).background(Yellow)
        ) {
            Text(text = "Weight = 2")
        }
    }
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(183.dp)
//            .clickable { },
//        shape = RoundedCornerShape(10.dp),
//        shadowElevation = 5.dp,
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            val image: Painter = painterResource(id = R.drawable.room)
//            Image(
//                painter = image,
//                contentDescription = item,
//                modifier = Modifier
//                    .weight(1f)
//                    .width(195.dp)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(10.dp))
//            )
//            Spacer(modifier = Modifier.height(32.dp))
//            Text(
//                "Hello World!2",
//                modifier = Modifier
//                    .weight(2f)
//            )
//        }
//    }
}




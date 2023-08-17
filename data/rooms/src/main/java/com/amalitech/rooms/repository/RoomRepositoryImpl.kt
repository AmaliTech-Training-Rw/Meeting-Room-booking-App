package com.amalitech.rooms.repository

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import kotlinx.coroutines.delay
import kotlin.random.Random


class RoomRepositoryImpl : RoomRepository {

    val images = listOf(
        "https://picsum.photos/id/29/4000/2670",
        "https://picsum.photos/id/0/5000/3333",
        "https://picsum.photos/id/15/2500/1667",
        "https://picsum.photos/id/26/4209/2769",
        "https://picsum.photos/id/3/5000/3333",
        "https://picsum.photos/id/4/5000/3333",
        "https://picsum.photos/id/5/5000/3334",
        "https://picsum.photos/id/6/5000/3333",
        "https://picsum.photos/id/7/4728/3168",
        "https://picsum.photos/id/8/5000/3333",
        "https://picsum.photos/id/9/5000/3269",
        "https://picsum.photos/id/10/2500/1667",
        "https://picsum.photos/id/11/2500/1667",
        "https://picsum.photos/id/12/2500/1667",
        "https://picsum.photos/id/5/5000/3334",
        "https://picsum.photos/id/13/2500/1667",
        "https://picsum.photos/id/1/5000/3333",
        "https://picsum.photos/id/14/2500/1667",
        "https://picsum.photos/id/16/2500/1667",
        "https://picsum.photos/id/17/2500/1667",
        "https://picsum.photos/id/10/2500/1667",
        "https://picsum.photos/id/18/2500/1667",
        "https://picsum.photos/id/19/2500/1667",
        "https://picsum.photos/id/20/3670/2462",
        "https://picsum.photos/id/22/4434/3729",
        "https://picsum.photos/id/23/3887/4899",
        "https://picsum.photos/id/24/4855/1803",
        "https://picsum.photos/id/25/5000/3333",
        "https://picsum.photos/id/27/3264/1836",
        "https://picsum.photos/id/28/4928/3264",
        "https://picsum.photos/id/33/5000/3333",
        "https://picsum.photos/id/30/1280/901",
        "https://picsum.photos/id/31/3264/4912",
        "https://picsum.photos/id/32/4032/3024"
    )
    val names = listOf(
        "Vessel Of Light",
        "Inspiration Lounge",
        "The Portable Space",
        "Think Out Loud",
        "IdeaWorks",
        "Thought Out",
        "Living The Story",
        "Wishpiration",
        "Nature Lovers",
        "Sharing Is Caring",
        "Vision 2020",
        "Eternal Hopes",
        "Vision Achievers",
        "One Goal",
        "One Vision",
        "Growing Horizon",
        "Success Majors",
        "Smart Choices",
        "Burning Desire",
        "Mind Conference",
        "Achievement Territory",
        "Fortune Seekers",
        "Idea Advancements",
        "Goal Oriented Minds",
        "Proficiency Group",
        "Group Effort",
        "Agents Of Change",
        "The Good Guys",
        "Focus Faction",
        "Success Cartel",
        "Winners Circle",
        "Inner Winners",
        "Stress Success",
        "Mind Binds"
    )
    private val rooms = (0..33).map {
        Room(
            id = it.toString(),
            roomName = names[it],
            numberOfPeople = Random.nextInt(2, 30),
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Whiteboard",
                "Natural light",
                "Drinks"
            ),
            imageUrl = images[it]
        )
    }.toMutableList()

    override suspend fun getRooms(): ApiResult<List<Room>> {
        delay(2000)
        return ApiResult(rooms)

    }

    override suspend fun deleteRoom(room: Room): UiText? {
        var exception: Exception? = null
        try {
            rooms.remove(room)
        } catch (e: Exception) {
            exception = e
        }
        return if (exception == null)
            null else exception.localizedMessage?.let { UiText.DynamicString(it) }
    }
}

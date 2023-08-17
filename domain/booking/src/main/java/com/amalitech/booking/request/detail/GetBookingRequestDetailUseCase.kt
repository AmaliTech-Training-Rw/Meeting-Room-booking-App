package com.amalitech.booking.request.detail

import com.amalitech.booking.model.Booking
import com.amalitech.booking.model.BookingRequestDetail
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime

class GetBookingRequestDetailUseCase {
    suspend operator fun invoke(id: String): ApiResult<BookingRequestDetail> {
        delay(1000)
        val images = listOf(
            "https://picsum.photos/id/29/4855/2000",
            "https://picsum.photos/id/0/4855/2000",
            "https://picsum.photos/id/15/4855/2000",
            "https://picsum.photos/id/26/4855/2000",
            "https://picsum.photos/id/3/4855/2000",
            "https://picsum.photos/id/4/4855/2000",
            "https://picsum.photos/id/5/4855/2000",
            "https://picsum.photos/id/6/4855/2000",
            "https://picsum.photos/id/7/4855/2000",
            "https://picsum.photos/id/8/4855/2000",
            "https://picsum.photos/id/9/4855/2000",
            "https://picsum.photos/id/10/4855/2000",
            "https://picsum.photos/id/11/4855/2000",
            "https://picsum.photos/id/12/4855/2000",
            "https://picsum.photos/id/13/4855/2000",
            "https://picsum.photos/id/1/4855/2000",
            "https://picsum.photos/id/14/4855/2000",
            "https://picsum.photos/id/16/4855/2000",
            "https://picsum.photos/id/17/4855/2000",
            "https://picsum.photos/id/10/4855/2000",
            "https://picsum.photos/id/18/4855/2000",
            "https://picsum.photos/id/19/4855/2000",
            "https://picsum.photos/id/20/4855/2000",
            "https://picsum.photos/id/22/4855/2000",
            "https://picsum.photos/id/9/4855/2000",
            "https://picsum.photos/id/23/4855/2000",
            "https://picsum.photos/id/24/4855/2000",
            "https://picsum.photos/id/25/4855/2000",
            "https://picsum.photos/id/27/4855/2000",
            "https://picsum.photos/id/28/4855/2000",
            "https://picsum.photos/id/33/4855/2000",
            "https://picsum.photos/id/30/4855/2000",
            "https://picsum.photos/id/31/4855/2000",
            "https://picsum.photos/id/32/4855/2000"
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

        return ApiResult(
            BookingRequestDetail(
                booking = Booking(
                    id = "id",
                    names.random(),
                    LocalDate.now(),
                    LocalTime.of((0..23).random(), 0),
                    LocalTime.of((0..23).random(), 0),
                    images.random(),
                    bookedBy = "johndoe@amalitech.org",
                    attendees = listOf(
                        "johndoe@gmail.com",
                        "johndoe@example.com",
                        "johndoe@amalitech.com",
                        "johndoe@amalitech.org"
                    ),
                    note = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                ),
                room = Room(
                    id = "id1",
                    roomName = names.random(),
                    numberOfPeople = 5,
                    roomFeatures = listOf(
                        "Air conditioning",
                        "Internet",
                        "Whiteboard",
                        "Natural light",
                        "Drinks"
                    ),
                    imageUrl = images.random()
                )
            )
        )
    }
}

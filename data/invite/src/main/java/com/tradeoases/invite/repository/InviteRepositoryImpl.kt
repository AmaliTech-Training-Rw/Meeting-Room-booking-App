package com.tradeoases.invite.repository

import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime

class InviteRepositoryImpl: InviteRepository {
    private val images = listOf(
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
    private val names = listOf(
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
    private val invites = (0..33).map {
        Invite(
            inviteId= it,
            roomName= names[it],
            date = LocalDate.now(),
            startTime = LocalTime.now(),
            endTime= LocalTime.MIDNIGHT,
            imageUrl = images[it]
        )
    }
    override suspend fun getInvites(): Flow<Invite> = flowOf(
        invites[0],
        invites[1],
        invites[2],
        invites[3],
        invites[4],
        invites[5],
        invites[6],
        invites[7],
        invites[8],
        invites[9],
        invites[10],
        invites[11],
        invites[12],
        invites[13]
    )
}

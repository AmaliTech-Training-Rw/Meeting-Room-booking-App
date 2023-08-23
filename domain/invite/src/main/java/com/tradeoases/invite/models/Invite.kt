package com.tradeoases.invite.models

import java.time.LocalDate
import java.time.LocalTime

data class Invite(
    val inviteId: Int,
    val roomName: String,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val imageUrl: String
)

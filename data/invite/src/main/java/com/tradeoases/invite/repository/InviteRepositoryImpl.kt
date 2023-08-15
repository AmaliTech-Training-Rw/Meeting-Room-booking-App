package com.tradeoases.invite.repository

import com.tradeoases.invite.models.Invite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month

class InviteRepositoryImpl: InviteRepository {
    override fun getInvites(): Flow<Invite> = flowOf(
        Invite(
            1,
            "Alpha",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        ),
        Invite(
            2,
            "Zulu",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        ),
        Invite(
            3,
            "Tango",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        ),
        Invite(
            4,
            "Alpha",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        ),
        Invite(
            5,
            "Zulu",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        ),
        Invite(
            6,
            "Tango",
            LocalDate.of(2020, Month.JANUARY, 8),
            LocalTime.now(),
            LocalTime.MIDNIGHT,
            ""
        )
    )
}

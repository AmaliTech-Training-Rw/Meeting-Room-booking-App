package com.amalitech.core.data

import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.network.BookMeetingNetworkApi

class BookMeetingRepositoryImpl(
    private val api: BookMeetingNetworkApi
) : BookMeetingRepository
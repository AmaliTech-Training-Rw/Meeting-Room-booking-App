package com.amalitech.core.data

import com.amalitech.core.domain.BookMeetingRepository
import com.amalitech.core.network.BookMeetingNetworkApi
import javax.inject.Inject

class BookMeetingRepositoryImpl @Inject constructor(
    private val api: BookMeetingNetworkApi
) : BookMeetingRepository
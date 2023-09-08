package com.amalitech.user.data_source.remote

data class UsersListDto(
    val count: Int?,
    val `data`: List<UsersData>?
)
package com.amalitech.core.data.data_source.remote.dto

import com.amalitech.core.data.model.Room
import com.squareup.moshi.Json

data class RoomsData(
    val capacity: String?,
    val features: List<FeatureDto>?,
    val id: Int?,
    val images: List<Image>?,
    @Json(name = "location_id")
    val locationId: Int?,
    val name: String?,
    @Json(name = "organisation_id")
    val organisationId: Int?,
    @Json(name = "user_id")
    val userId: Int?
) {
    fun toRoom(): Room {
        return Room(
            id = (id ?: -1).toString(),
            roomName = name ?: "",
            numberOfPeople = capacity?.toInt() ?: 0,
            roomFeatures = features?.map { it.name } ?: emptyList(),
            imageUrl = images?.map { it.url ?: "" } ?: emptyList(),
            locationId = locationId ?: -1
        )
    }
}

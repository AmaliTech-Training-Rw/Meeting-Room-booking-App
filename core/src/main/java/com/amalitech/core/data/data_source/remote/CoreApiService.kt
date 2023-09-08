package com.amalitech.core.data.data_source.remote

import com.amalitech.core.domain.model.LocationDto
import retrofit2.Response
import retrofit2.http.GET

interface CoreApiService {
    @GET("locations")
    suspend fun fetchLocations(): Response<LocationDto>
}

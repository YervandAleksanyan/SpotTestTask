package com.yervand.core.network

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SpotsApi {

    @GET("/api/test/points")
    suspend fun getSpots(@QueryMap parameters: Map<String, String>): SpotsResponse
}
package com.example.dicodingevent.data.network

import com.example.dicodingevent.data.network.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/events")
    suspend fun getByCategory(
        @Query("active") status: Int
    ) : EventResponse

    @GET("/events")
    suspend fun searchEvents(
        @Query("q") query: String
    ) : EventResponse
}
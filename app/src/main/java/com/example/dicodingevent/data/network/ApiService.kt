package com.example.dicodingevent.data.network

import com.example.dicodingevent.data.network.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/events")
    fun getByCategory(
        @Query("active") status: Int
    ) : Call<EventResponse>

    @GET("/events")
    fun searchEvents(
        @Query("q") query: String
    ) : Call<EventResponse>
}
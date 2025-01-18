package com.example.dicodingevent.data.network

import com.example.dicodingevent.data.network.response.EventResponse
import com.example.dicodingevent.data.network.response.Events
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/events")
    fun getAllEvents() : Call<EventResponse>

    @GET("/events")
    fun getByCategory(
        @Query("status") status: Int
    ) : Call<EventResponse>

    @GET("/events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int
    ) : Call<Events>
}
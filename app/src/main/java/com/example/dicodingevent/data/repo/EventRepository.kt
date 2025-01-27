package com.example.dicodingevent.data.repo


import com.example.dicodingevent.data.network.ApiService
import com.example.dicodingevent.data.network.response.EventResponse


class EventRepository private constructor(
    private val apiService: ApiService
){
    suspend fun getEvent(id: Int): EventResponse {
        return apiService.getByCategory(id)
    }

    suspend fun searchEvent(query: String) : EventResponse {
        return apiService.searchEvents(query)
    }

    companion object{
        @Volatile
        private var instansce : EventRepository? = null
        fun getInstansce(
            apiService: ApiService
        ) : EventRepository =
            instansce?: synchronized(this){
                instansce?: EventRepository(apiService)
            }.also { instansce = it }
    }
 }
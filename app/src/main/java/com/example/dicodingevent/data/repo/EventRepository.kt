package com.example.dicodingevent.data.repo


import com.example.dicodingevent.data.local.EventDAO
import com.example.dicodingevent.data.network.ApiService
import com.example.dicodingevent.data.network.response.EventResponse
import com.example.dicodingevent.data.network.response.Events


class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDAO
){
    suspend fun getEvent(id: Int): EventResponse {
        return apiService.getByCategory(id)
    }

    suspend fun searchEvent(query: String) : EventResponse {
        return apiService.searchEvents(query)
    }

    suspend fun getAllBookmarkedEvents() : List<Events>{
        return eventDao.getAllBookmarkedEvents()
    }

    suspend fun addBookmark(event: Events) {
        eventDao.insertEvent(event)
    }

    suspend fun removeBookmark(event: Events) {
        eventDao.deleteEvent(event)
    }

    suspend fun isBookmarked(eventId: Int): Boolean {
        return eventDao.getEventById(eventId) != null
    }



    companion object{
        @Volatile
        private var instansce : EventRepository? = null
        fun getInstansce(
            apiService: ApiService,
            eventDAO: EventDAO
        ) : EventRepository =
            instansce?: synchronized(this){
                instansce?: EventRepository(apiService,eventDAO)
            }.also { instansce = it }
    }
 }
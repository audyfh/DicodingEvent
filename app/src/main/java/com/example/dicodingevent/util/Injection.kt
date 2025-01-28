package com.example.dicodingevent.util

import android.content.Context
import com.example.dicodingevent.data.local.EventDB
import com.example.dicodingevent.data.network.ApiConfig
import com.example.dicodingevent.data.repo.EventRepository

object Injection {

    fun provideRepository(context: Context) : EventRepository{
        val apiService = ApiConfig.getApiService()
        val database = EventDB.getInstansce(context)
        val dao = database.eventDao()
        return EventRepository.getInstansce(apiService,dao)
    }
}
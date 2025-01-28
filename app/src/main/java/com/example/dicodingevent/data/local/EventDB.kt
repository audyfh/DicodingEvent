package com.example.dicodingevent.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.dicodingevent.data.network.response.Events

@Database(entities = [Events::class], version = 1, exportSchema = false)
abstract class EventDB : RoomDatabase() {
    abstract fun eventDao() : EventDAO

    companion object{
        @Volatile
        private var instansce: EventDB? = null
        fun getInstansce(context: Context) : EventDB =
            instansce?: Room.databaseBuilder(
                context.applicationContext,
                EventDB::class.java,"EventDB"
            ).build()
    }
}
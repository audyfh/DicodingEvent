package com.example.dicodingevent.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dicodingevent.data.network.response.Events

@Dao
interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Events)

    @Delete
    suspend fun deleteEvent(event: Events)

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Int): Events?

    @Query("SELECT * FROM events")
    suspend fun getAllBookmarkedEvents(): List<Events>
}
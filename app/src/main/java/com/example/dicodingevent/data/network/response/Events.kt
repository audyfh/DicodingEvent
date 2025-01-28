package com.example.dicodingevent.data.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "events")
data class Events(
    val beginTime: String,
    val category: String,
    val cityName: String,
    val description: String,
    val endTime: String,
    @PrimaryKey
    val id: Int,
    val imageLogo: String,
    val link: String,
    val mediaCover: String,
    val name: String,
    val ownerName: String,
    val quota: Int,
    val registrants: Int,
    val summary: String
) : Parcelable
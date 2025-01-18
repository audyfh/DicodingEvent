package com.example.dicodingevent.data.network.response

data class EventResponse(
    val error: Boolean,
    val listEvents: List<Events>,
    val message: String
)
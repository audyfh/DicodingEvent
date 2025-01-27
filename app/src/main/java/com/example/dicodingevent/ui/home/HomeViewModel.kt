package com.example.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import com.example.dicodingevent.util.Result
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: EventRepository
): ViewModel() {

    private val _event = MutableLiveData<Result<List<Events>>>()
    val event : LiveData<Result<List<Events>>> = _event

    private val _upcomingEvent = MutableLiveData<Result<List<Events>>>()
    val upcomingEvent : LiveData<Result<List<Events>>> = _upcomingEvent

    init {
        getUpcomingEvent()
        getFinishedEvent()
    }

    private fun getUpcomingEvent(){
        viewModelScope.launch {
            try {
                _upcomingEvent.value = Result.Loading
                val response = repository.getEvent(1).listEvents
                if (response.isNotEmpty()){
                    _upcomingEvent.value = Result.Success(response)
                } else {_upcomingEvent
                   _upcomingEvent.value = Result.Empty
                }
            } catch (e: Exception){
                _upcomingEvent.value = Result.Error(e.message.toString())
            }
        }
    }

    private fun getFinishedEvent(){
        viewModelScope.launch {
            try {
                _event.value = Result.Loading
                val response = repository.getEvent(0).listEvents
                if (response.isNotEmpty()){
                    _event.value = Result.Success(response)
                } else {
                    _event.value = Result.Loading
                }
            } catch (e: Exception){
                _event.value = Result.Error(e.message.toString())
            }
        }
    }
}
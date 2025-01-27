package com.example.dicodingevent.ui.upcoming


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import com.example.dicodingevent.util.Result
import kotlinx.coroutines.launch


class UpcomingViewModel(
    private val repository: EventRepository
): ViewModel() {

    private val _event = MutableLiveData<Result<List<Events>>>()
    val event : LiveData<Result<List<Events>>> = _event

    init {
        getUpcomingEvent()
    }

    private fun getUpcomingEvent(){
       viewModelScope.launch {
           try {
               _event.value = Result.Loading
               val response = repository.getEvent(1).listEvents
               if (response.isNotEmpty()){
                   _event.value = Result.Success(response)
               } else {
                   _event.value = Result.Empty
               }
           } catch (e: Exception) {
               _event.value = Result.Error(e.message.toString())
           }
       }
    }
}
package com.example.dicodingevent.ui.finished


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import kotlinx.coroutines.launch
import com.example.dicodingevent.util.Result


class FinishedViewModel(
    private val eventRepository: EventRepository
): ViewModel() {
    private val _event = MutableLiveData<Result<List<Events>>>()
    val event : LiveData<Result<List<Events>>> = _event

    init {
       getFinishedEvent()
    }

    private fun getFinishedEvent(){
        viewModelScope.launch {
            try {
                _event.value = Result.Loading
                val response = eventRepository.getEvent(0).listEvents
                if (response.isNotEmpty()){
                    _event.value = Result.Success(response)
                } else {
                    _event.value = Result.Loading
                }
            } catch (e: Exception) {
                _event.value = Result.Error(e.message.toString())
            }
        }
    }
}
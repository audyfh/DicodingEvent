package com.example.dicodingevent.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import com.example.dicodingevent.util.Result
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private var _event = MutableLiveData<Result<List<Events>>>()
    val event : LiveData<Result<List<Events>>> = _event

    init {
        getEvents()
    }

    fun getEvents(){
        viewModelScope.launch {
            val response = repository.getAllBookmarkedEvents()
            if (response.isNotEmpty()){
                _event.value = Result.Success(response)
            } else {
                _event.value = Result.Empty
            }
        }
    }
}
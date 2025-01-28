package com.example.dicodingevent.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val _isBookmarked = MutableLiveData<Boolean>()
    val isBookMarked : LiveData<Boolean> = _isBookmarked

    fun checkIfBookmarked(eventId: Int) {
        viewModelScope.launch {
            _isBookmarked.value = repository.isBookmarked(eventId)
        }
    }

    fun toggleBookmark(event: Events) {
        viewModelScope.launch {
            val bookmarked = repository.isBookmarked(event.id)
            if (bookmarked) {
                repository.removeBookmark(event)
                _isBookmarked.value = false
            } else {
                repository.addBookmark(event)
                _isBookmarked.value = true
            }
        }
    }
}
package com.example.dicodingevent.ui.search
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.data.repo.EventRepository
import com.example.dicodingevent.util.Result
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: EventRepository
) : ViewModel() {
    private val _searchResults = MutableLiveData<Result<List<Events>>>()
    val searchResults: LiveData<Result<List<Events>>> = _searchResults

    fun searchEvents(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = Result.Loading
                val response = repository.searchEvent(query).listEvents
                if (response.isNotEmpty()){
                    _searchResults.value = Result.Success(response)
                } else {
                    _searchResults.value = Result.Empty
                }
            } catch (e: Exception){
                _searchResults.value = Result.Error(e.message.toString())
            }
        }
    }
}
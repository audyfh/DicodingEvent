package com.example.dicodingevent.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.network.ApiConfig
import com.example.dicodingevent.data.network.response.EventResponse
import com.example.dicodingevent.data.network.response.Events
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableLiveData<List<Events>>()
    val searchResults: LiveData<List<Events>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun searchEvents(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchEvents(query)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchResults.value = response.body()?.listEvents
                } else {
                    _searchResults.value = emptyList()
                    Log.e("HomeViewModel", "Search failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _searchResults.value = emptyList()
                Log.e("HomeViewModel", "Search error: ${t.message}")
            }
        })
    }
}
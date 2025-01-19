package com.example.dicodingevent.ui.upcoming

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

class UpcomingViewModel : ViewModel() {

    private val _event = MutableLiveData<List<Events>>()
    val event : LiveData<List<Events>> = _event

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getUpcomingEvent()
    }

    private fun getUpcomingEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getByCategory(1)

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _event.value = response.body()?.listEvents
                } else {
                    _event.value = emptyList()
                    Log.e("UpcomingViewModel",response.message())
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _event.value = emptyList()
                _isLoading.value = false
                Log.e("UpcomingViewModel", t.message.toString())
            }

        })
    }
}
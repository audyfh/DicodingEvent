package com.example.dicodingevent.ui.home

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

class HomeViewModel : ViewModel() {

    private val _event = MutableLiveData<List<Events>>()
    val event : LiveData<List<Events>> = _event

    private val _upcomingEvent = MutableLiveData<List<Events>>()
    val upcomingEvent : LiveData<List<Events>> = _upcomingEvent


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUpcomingEvent()
        getAllEvent()
    }

    private fun getUpcomingEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getByCategory(0)

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _upcomingEvent.value = response.body()?.listEvents?.take(5)
                } else {
                    Log.e("HomeViewModel",response.message())
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }

    private fun getAllEvent(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getByCategory(1)

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _event.value = response.body()?.listEvents
                } else {
                    Log.e("HomeViewModel",response.message())
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
               _isLoading.value = false
                Log.e("HomeViewModel", t.message.toString())
            }
        })

    }

}
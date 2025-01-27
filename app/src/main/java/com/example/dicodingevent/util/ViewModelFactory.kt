package com.example.dicodingevent.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.data.repo.EventRepository
import com.example.dicodingevent.ui.finished.FinishedViewModel
import com.example.dicodingevent.ui.home.HomeViewModel
import com.example.dicodingevent.ui.search.SearchViewModel
import com.example.dicodingevent.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(
    private val repository: EventRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FinishedViewModel::class.java)){
            return FinishedViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)){
            return UpcomingViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context)
                ).also { instance = it }
            }
    }
}
package com.example.dicodingevent.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dicodingevent.util.Injection
import com.example.dicodingevent.util.NotificationWorker
import com.example.dicodingevent.util.PreferencesRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SettingViewModel(
    private val preferencesRepository: PreferencesRepository,
) : ViewModel(){

    val darkMode: LiveData<Boolean> = preferencesRepository.darkModeFlow.asLiveData()

    fun setDarkMode(enabled: Boolean){
        viewModelScope.launch {
            preferencesRepository.setDarkMode(enabled)
        }
    }

    fun setNotificationEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setNotificationEnabled(isEnabled)
            if (isEnabled) {
                scheduleNotificationWork()
            } else {
                cancelNotificationWork()
            }
        }
    }

    private fun scheduleNotificationWork() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "EventNotificationWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    private fun cancelNotificationWork() {
        WorkManager.getInstance().cancelUniqueWork("EventNotificationWork")
    }

}

@Suppress("UNCHECKED_CAST")
class SettingViewModelFactory(
    private val repository: PreferencesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object{
        @Volatile
        private var instance : SettingViewModelFactory? = null
        fun getInstansce(context: Context) : SettingViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SettingViewModelFactory(
                    Injection.providePrefRepository(context)
                ).also { instance = it }
            }
    }
}
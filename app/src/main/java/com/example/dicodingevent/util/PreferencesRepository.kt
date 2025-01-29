package com.example.dicodingevent.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.dicodingevent.util.PreferencesRepository.PreferencesKeys.DARK_MODE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: PreferencesRepository? = null

        fun getInstance(context: Context): PreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferencesRepository(
                    PreferenceDataStoreFactory.create {
                        context.applicationContext.preferencesDataStoreFile("settings")
                    }
                )
                INSTANCE = instance
                instance
            }
        }
    }

    private object PreferencesKeys {
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    val darkModeFlow: Flow<Boolean> = dataStore.data.map {
        it[DARK_MODE_KEY] ?: false
    }

    suspend fun setNotificationEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = isEnabled
        }
    }

    suspend fun setDarkMode(enabled: Boolean){
        dataStore.edit {
            it[DARK_MODE_KEY] = enabled
        }
    }
}
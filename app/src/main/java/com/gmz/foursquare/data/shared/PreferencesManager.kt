package com.gmz.foursquare.data.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Singleton

@Singleton
class PreferencesManager(context: Context) {

    companion object {
        private val tokenKey = stringPreferencesKey("token_key")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
    }

    private val dataStore = context.dataStore

    suspend fun setToken(token: String) {
        dataStore.edit { pref ->
            pref[tokenKey] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { pref ->
                val token = pref[tokenKey] ?: ""
                token
            }
    }

    suspend fun deleteAllPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
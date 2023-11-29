package com.example.aneon_test_case.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey


object DataStoreManager {
    private const val PREFERENCES_NAME = "app_preferences"
    private val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
            by preferencesDataStore(
                name = PREFERENCES_NAME
            )

    suspend fun saveAuthToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }

    fun readAuthToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }
    }

    suspend fun saveIsUserLoggedIn(context: Context, isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_USER_LOGGED_IN] = isLoggedIn
        }
    }

    fun readIsUserLoggedIn(context: Context): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_USER_LOGGED_IN] ?: false
        }
    }

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    }
}

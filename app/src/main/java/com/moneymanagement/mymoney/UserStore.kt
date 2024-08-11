package com.moneymanagement.mymoney

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userId")
        private val USER_ID = intPreferencesKey("user_id")
    }

    val getAccessToken: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[USER_ID] ?: 0
    }

    suspend fun saveToken(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }
}
package com.jordan.lab_week_11_a

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Setup DataStore Extension (Singleton)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingsStore")

class SettingsStore(private val context: Context) {

    // 2. Companion object for Keys
    companion object {
        val KEY_TEXT = stringPreferencesKey("key_text")
    }

    // 3. The Flow to read data
    val text: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TEXT] ?: ""
        }

    // 4. Suspend function to write data
    suspend fun saveText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TEXT] = text
        }
    }
}
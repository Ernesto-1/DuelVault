package com.myapp.duelvault.utils.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Duel_preferences")


    val currentOffset: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.currentOffset] ?: 0
        }

    suspend fun saveOffset(offset: Int) {
        savePreference(PreferencesKeys.currentOffset, offset)
    }

    private suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}

private object PreferencesKeys {

    val currentOffset = intPreferencesKey("offset")

}
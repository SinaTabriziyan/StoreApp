package com.sina.local.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_TYPE_POSITION
import com.sina.common.constants.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.sina.common.constants.Constants.Companion.PREFERENCES_NAME
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_TYPE
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class AppDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKey {
        val selectedSearchType = stringPreferencesKey(PREFERENCES_SEARCH_TYPE)
        val selectedSearchTypeID = intPreferencesKey(PREFERENCES_SEARCH_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

    suspend fun saveMealAndDietType(
        searchType: String,
        searchTypeId: Int,
        searchOrderTypeChip: String,
        searchOrderTypeIdChip: Int,
    ) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.selectedSearchType] = searchType
            preferences[PreferencesKey.selectedSearchTypeID] = searchTypeId
            // TODO:  
//            preferences[PreferencesKey.selectedSearchType] = searchType
//            preferences[PreferencesKey.selectedSearchTypeID] = searchTypeId

        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) = context.dataStore.edit { preferences ->
        preferences[PreferencesKey.backOnline] = backOnline
    }

    val readBackOnline: Flow<Boolean> = context.dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences: Preferences ->
        val backOnline =
            preferences[PreferencesKey.backOnline] ?: false;backOnline
    }


    val readSearchType: Flow<SearchType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception

        }.map { preferences ->
            val selectedSearchType = preferences[PreferencesKey.selectedSearchType] ?: DEFAULT_SEARCH_TYPE
            val selectedSearchTypeId =
                preferences[PreferencesKey.selectedSearchTypeID] ?: DEFAULT_SEARCH_TYPE_POSITION
            SearchType(
                selectedSearchType,
                selectedSearchTypeId,
            )
        }
}

data class SearchType(
    val selectedSearchType: String,
    val selectedSearchTypeId: Int,

    )
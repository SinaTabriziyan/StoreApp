package com.sina.local.data.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE_ID
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE
import com.sina.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE_ID
import com.sina.common.constants.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.sina.common.constants.Constants.Companion.PREFERENCES_NAME
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_BY_TYPE
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_BY_TYPE_ID
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_TYPE
import com.sina.common.constants.Constants.Companion.PREFERENCES_SEARCH_ORDER_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class AppDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferencesKey {
//        val selectedSearchType = stringPreferencesKey(PREFERENCES_SEARCH_TYPE)
//        val selectedSearchTypeID = intPreferencesKey(PREFERENCES_SEARCH_TYPE_ID)

        val selectedSearchOrderType = stringPreferencesKey(PREFERENCES_SEARCH_ORDER_TYPE)
        val selectedSearchOrderTypeId = intPreferencesKey(PREFERENCES_SEARCH_ORDER_TYPE_ID)

        val selectedSearchOrderByType = stringPreferencesKey(PREFERENCES_SEARCH_ORDER_BY_TYPE)
        val selectedSearchOrderByTypeId = intPreferencesKey(PREFERENCES_SEARCH_ORDER_BY_TYPE_ID)

        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

    suspend fun saveSearchOrderType(searchOrderTypeChip: String, searchOrderTypeIdChip: Int) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.selectedSearchOrderType] = searchOrderTypeChip
            mutablePreferences[PreferencesKey.selectedSearchOrderTypeId] = searchOrderTypeIdChip
        }
    }

    suspend fun saveSearchOrderByType(searchOrderByType: String, searchOrderByTypeId: Int) {
        Timber.e("Filters before $searchOrderByType")
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKey.selectedSearchOrderByType] = searchOrderByType
            mutablePreferences[PreferencesKey.selectedSearchOrderByTypeId] = searchOrderByTypeId
            Timber.e("Filters: after dataStore$searchOrderByType,$searchOrderByTypeId")
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
            val selectedSearchOrderType = preferences[PreferencesKey.selectedSearchOrderType] ?: DEFAULT_SEARCH_ORDER_TYPE
            val selectedSearchOrderTypeId = preferences[PreferencesKey.selectedSearchOrderTypeId] ?: DEFAULT_SEARCH_ORDER_TYPE_ID
            val selectedSearchOrderByType = preferences[PreferencesKey.selectedSearchOrderByType] ?: DEFAULT_SEARCH_ORDER_BY_TYPE
            val selectedSearchOrderByTypeId = preferences[PreferencesKey.selectedSearchOrderByTypeId] ?: DEFAULT_SEARCH_ORDER_BY_TYPE_ID
            Timber.e("Filters OrderByType:: $selectedSearchOrderByType")

            SearchType(
                selectedSearchOrderType,
                selectedSearchOrderTypeId,
                selectedSearchOrderByType,
                selectedSearchOrderByTypeId
            )
        }
}

data class SearchType(
    val selectedSearchOrderType: String,
    val selectedSearchOrderTypeId: Int,
    val selectedSearchOrderByType: String,
    val selectedSearchOrderByTypeId: Int,
)
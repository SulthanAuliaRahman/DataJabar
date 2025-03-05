package com.example.jtkwibu.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object OnboardingPrefs {
    private const val DS_NAME = "onboarding_prefs"
    private val KEY_SHOWN = booleanPreferencesKey("onboarding_shown")
    private val KEY_NAME = stringPreferencesKey("user_name")
    private val KEY_NIM = stringPreferencesKey("user_nim")

    val Context.onboardingDataStore by preferencesDataStore(name = DS_NAME)

    suspend fun setOnboardingShown(context: Context) {
        context.onboardingDataStore.edit { it[KEY_SHOWN] = true }
    }

    fun isOnboardingShown(context: Context): Flow<Boolean> {
        return context.onboardingDataStore.data.map { it[KEY_SHOWN] ?: false }
    }

    // Fungsi untuk menyimpan nama & NIM
    suspend fun saveUserData(context: Context, name: String, nim: String) {
        context.onboardingDataStore.edit { prefs ->
            prefs[KEY_NAME] = name
            prefs[KEY_NIM] = nim
        }
    }

    // Fungsi untuk mengambil nama
    fun getUserName(context: Context): Flow<String> {
        return context.onboardingDataStore.data.map { it[KEY_NAME] ?: "" }
    }

    // Fungsi untuk mengambil NIM
    fun getUserNim(context: Context): Flow<String> {
        return context.onboardingDataStore.data.map { it[KEY_NIM] ?: "" }
    }
}

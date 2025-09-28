package com.myapp.voicehealth.core.storage

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("VoiceHealthPrefs", Context.MODE_PRIVATE)

    fun saveAccessToken(token: String) {
        prefs.edit().putString("ACCESS_TOKEN", token).apply()
    }

    fun getAccessToken(): String? = prefs.getString("ACCESS_TOKEN", null)

    fun saveUserId(userId: String) {
        prefs.edit().putString("USER_ID", userId).apply()
    }

    fun getUserId(): String? = prefs.getString("USER_ID", null)

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("IS_LOGGED_IN", isLoggedIn).apply()
    }

    fun isUserLoggedIn(): Boolean = prefs.getBoolean("IS_LOGGED_IN", false)

    fun clear() {
        prefs.edit().clear().apply()
    }
}

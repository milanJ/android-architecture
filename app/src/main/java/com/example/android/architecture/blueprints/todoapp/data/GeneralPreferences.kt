package com.example.android.architecture.blueprints.todoapp.data

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi

class GeneralPreferences constructor(context: Context, private val moshi: Moshi) {

    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    var authToken: String?
        get() = preferences.getString(PREF_AUTH_TOKEN, null)
        set(value) = if (value == null) {
            editor.remove(PREF_AUTH_TOKEN)
                    .apply()
        } else {
            editor.putString(PREF_AUTH_TOKEN, value)
                    .apply()
        }

    var signedInUser: String?
        get() = preferences.getString(PREF_USER_ID, null)
        set(value) = if (value == null) {
            editor.remove(PREF_USER_ID)
                    .apply()
        } else {
            editor.putString(PREF_USER_ID, value)
                    .apply()
        }

    init {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    companion object {
        const val PREF_NAME = "com.example.android.architecture.blueprints.todoapp.data.general-preferences"
        const val PREF_AUTH_TOKEN = "pref_auth_token"
        const val PREF_USER_ID = "pref_user_id"
    }
}

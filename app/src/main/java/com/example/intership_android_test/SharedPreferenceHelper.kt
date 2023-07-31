package com.example.intership_android_test

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferenceHelper {

    private const val PREF_NAME = "UserPrefs"
    private const val KEY_USERS = "users"


    fun saveUsers(context: Context, userList: List<User>) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonUsers = gson.toJson(userList)
        sharedPreferences.edit().putString(KEY_USERS, jsonUsers).apply()
    }

    fun getUsers(context: Context): ArrayList<User> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val jsonUsers = sharedPreferences.getString(KEY_USERS, "")
        val type = object : TypeToken<ArrayList<User>>() {}.type
        return gson.fromJson(jsonUsers, type) ?: ArrayList<User>()
    }
}
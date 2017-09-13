package com.application.kotproject

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.application.kotproject.login.LoginActivity
import com.application.kotproject.model.User
import com.google.gson.Gson

class App : Application() {

    companion object {
        const val USER = "SharedPreferences.USER"

        lateinit var sInstance: App
            private set

        fun getUser(): User? {
            val userJson = sInstance.mSharedPreferences.getString(USER, null)
            if (userJson != null)
                return Gson().fromJson(userJson, User::class.java)
            return null
        }

        fun setUser(user: User) = sInstance.mSharedPreferences.edit().putString(USER, Gson().toJson(user)).apply()

        fun resetUser() = sInstance.mSharedPreferences.edit().remove(USER).apply()
    }

    lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sInstance = this
        mSharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

        if (getUser() == null) {
            val loginIntent = Intent(this, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(loginIntent)
        }
    }


}

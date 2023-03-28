package com.example.mvvmrepositorylab

import android.app.Application
import com.example.mvvmrepositorylab.local.UserDatabase
import com.example.mvvmrepositorylab.remote.RetrofitClient
import retrofit2.Retrofit

class UserApp: Application() {
    companion object {
        // Retrofit
        lateinit var retrofit: Retrofit
        // Room
        lateinit var userDatabase: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // Retrofit
        retrofit = RetrofitClient.getInstance()
        // Room
        userDatabase = UserDatabase.getInstance(applicationContext)
    }


}
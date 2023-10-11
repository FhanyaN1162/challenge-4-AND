package com.example.challenge4fn

import androidx.room.Room
import android.app.Application
import com.example.challenge4fn.database.AppDatabase

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi database Room
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
    }
}

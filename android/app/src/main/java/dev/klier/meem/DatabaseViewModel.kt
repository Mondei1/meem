package dev.klier.meem

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import dev.klier.meem.models.AppDatabase

class DatabaseViewModel : ViewModel() {
    private lateinit var db: AppDatabase

    fun init(context: Context) {
        db = Room
            .databaseBuilder(context, AppDatabase::class.java, "meem")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun getDb() = db

}
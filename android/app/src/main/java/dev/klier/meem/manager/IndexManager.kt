package dev.klier.meem.manager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import dev.klier.meem.models.AppDatabase
import dev.klier.meem.models.Media
import dev.klier.meem.types.PhoneAlbum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class IndexManager(context: Context): ViewModel() {
    private val db: AppDatabase
    private val appContext: Context

    init {
        db = Room
            .databaseBuilder(context, AppDatabase::class.java, "meem")
            .fallbackToDestructiveMigration()
            .build()
        appContext = context
    }

    fun importAlbum(albums: List<Media>): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            db.mediaDao().bulkInsert(albums)
        }
    }
}
package dev.klier.meem.manager

import android.content.Context
import androidx.room.Room
import dev.klier.meem.models.AppDatabase
import dev.klier.meem.types.PhoneAlbum


class IndexManager(context: Context) {
    private val db: AppDatabase

    init {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "meem").build()
    }

    fun importAlbum(albums: List<PhoneAlbum>) {
        db.mediaDao().insert()
    }
}
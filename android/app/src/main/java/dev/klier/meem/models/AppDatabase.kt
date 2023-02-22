package dev.klier.meem.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Media::class, Tag::class, MediaTagCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun tagDao(): TagDao
}
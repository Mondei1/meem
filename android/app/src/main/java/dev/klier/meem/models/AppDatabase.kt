package dev.klier.meem.models

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [
    Media::class,
    Tag::class,
    MediaTagCrossRef::class,
    Setting::class
], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun tagDao(): TagDao
    abstract fun settingDao(): SettingDao
}
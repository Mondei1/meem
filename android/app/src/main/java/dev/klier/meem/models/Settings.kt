package dev.klier.meem.models

import androidx.lifecycle.LiveData
import androidx.room.*
import java.net.URI

@Entity
data class Setting(
    @PrimaryKey val name: String,
    @ColumnInfo val value: String
)

@Dao
interface SettingDao {
    @Query("SELECT * FROM setting")
    fun getAll(): List<Setting>

    @Query("SELECT value FROM setting WHERE name = :name")
    fun getStringByName(name: String): String?

    @Query("SELECT value FROM setting WHERE name = :name")
    fun getUriByName(name: String): URI?

    @Query("SELECT value FROM setting WHERE name = :name")
    fun getIntByName(name: String): LiveData<Int?>

    @Insert
    fun insert(vararg setting: Setting)

    @Delete
    fun delete(setting: Setting)
}
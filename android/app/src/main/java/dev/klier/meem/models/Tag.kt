package dev.klier.meem.models

import androidx.room.*

@Entity
data class Tag(
    @PrimaryKey val tagId: Int,
    @ColumnInfo(name = "name") val name: String
)

@Dao
interface TagDao {
    @Query("SELECT * FROM tag")
    fun getAll(): List<Tag>

    @Query("SELECT * FROM tag WHERE tagId = :id")
    fun getById(id: Int): Tag

    @Insert
    fun insert(vararg tag: Tag)

    @Delete
    fun delete(tag: Tag)
}
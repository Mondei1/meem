package dev.klier.meem.models

import androidx.room.*

@Entity
data class Media(
    @PrimaryKey val mediaId: Int,
    @ColumnInfo(name = "album_id") val album: Int,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "changed_at") val changedAt: String
)

data class MediaWithTags(
    @Embedded val media: Media,
    @Relation(
        parentColumn = "mediaId",
        entityColumn = "tagId",
        associateBy = Junction(MediaTagCrossRef::class)
    )
    val tags: List<Tag>
)

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    fun getAll(): List<Media>

    @Transaction
    @Query("SELECT * FROM media")
    fun getMediaWithTags(): List<MediaWithTags>

    @Transaction
    fun bulkInsert(mediaList: List<Media>) {
        for (media in mediaList) {
            insert(media)
        }
    }

    @Insert
    fun insert(vararg media: Media)

    @Delete
    fun delete(media: Media)
}
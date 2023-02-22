package dev.klier.meem.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(primaryKeys = ["mediaId", "tagId"])
data class MediaTagCrossRef(
    val mediaId: Int,
    val tagId: Int
)

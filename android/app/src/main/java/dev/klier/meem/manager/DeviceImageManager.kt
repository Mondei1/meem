package dev.klier.meem.manager

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.MediaStore
import android.util.Log
import dev.klier.meem.types.PhoneAlbum
import dev.klier.meem.types.PhonePhoto
import java.security.KeyPair
import java.util.*

class DeviceImageManager : java.io.Serializable {

    // List of album ids
    // (Index of album selection, Album Id)
    var selected: List<Pair<Int, Int>> = listOf()
    var albumCache: List<PhoneAlbum?> = listOf()

    companion object Factory {
        fun getInstance(): DeviceImageManager = DeviceImageManager()
    }

    // Thanks to @Barakuda on StackOverflow
    // https://stackoverflow.com/questions/4195660/get-list-of-photo-galleries-on-android
    fun populateCache(context: Context): List<PhoneAlbum?>? {
        // Creating vectors to hold the final albums objects and albums names
        val phoneAlbums: MutableList<PhoneAlbum?> = Vector()
        val albumsNames: MutableList<String> = Vector()

        // which image properties are we querying
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID
        )

        // content: style URI for the "primary" external storage volume
        val images: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        // Make the query.
        val cur: Cursor? = context.contentResolver?.query(
            images,
            projection,  // Which columns to return
            null,  // Which rows to return (all rows)
            null,  // Selection arguments (none)
            null // Ordering
        )
        if (cur != null && cur.count > 0) {
            Log.i("DeviceImageManager", " query count=" + cur.count)
            if (cur.moveToFirst()) {
                var bucketName: String
                var data: String
                var imageId: String
                val bucketNameColumn: Int = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                )
                val imageUriColumn: Int = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA
                )
                val imageIdColumn: Int = cur.getColumnIndex(
                    MediaStore.Images.Media._ID
                )
                do {
                    // Get the field values
                    bucketName = cur.getString(bucketNameColumn)
                    data = cur.getString(imageUriColumn)
                    imageId = cur.getString(imageIdColumn)

                    // Adding a new PhonePhoto object to phonePhotos vector
                    val phonePhoto = PhonePhoto()
                    phonePhoto.albumName = bucketName
                    phonePhoto.photoUri = data
                    phonePhoto.id = Integer.valueOf(imageId)
                    if (albumsNames.contains(bucketName)) {
                        for (album in phoneAlbums) {
                            if (album?.name == bucketName) {
                                album?.albumPhotos!!.add(phonePhoto)
                                break
                            }
                        }
                    } else {
                        // Create new album as we found first image of an unknown bucket.
                        val album = PhoneAlbum()
                        Log.i("DeviceImageManager", "A new album was created => $bucketName")
                        album.id = phonePhoto.id
                        album.name = bucketName
                        album.coverUri = Uri.parse(phonePhoto.photoUri)
                        album.albumPhotos!!.add(phonePhoto)
                        Log.i("DeviceImageManager", "A photo was added to album => $bucketName")
                        phoneAlbums.add(album)
                        albumsNames.add(bucketName)
                    }
                } while (cur.moveToNext())
            }
            cur.close()

            this.albumCache = phoneAlbums.toList()

            return phoneAlbums
        } else {
            return null
        }
    }

}
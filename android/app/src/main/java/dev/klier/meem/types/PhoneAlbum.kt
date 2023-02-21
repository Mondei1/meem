package dev.klier.meem.types

import android.net.Uri
import java.util.*

class PhoneAlbum {
    var id = 0
    var name: String? = null
    var coverUri: Uri? = null
    var albumPhotos: Vector<PhonePhoto>? = null
        get() {
            if (field == null) {
                field = Vector()
            }
            return field
        }
}

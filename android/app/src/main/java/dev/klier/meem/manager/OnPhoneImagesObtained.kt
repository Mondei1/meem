package dev.klier.meem.manager

import dev.klier.meem.types.PhoneAlbum
import java.util.*

interface OnPhoneImagesObtained {
    fun onComplete(albums: Vector<PhoneAlbum?>?)
    fun onError()
}
package dev.klier.meem.activities.setup

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.klier.meem.manager.DeviceImageManager
import dev.klier.meem.types.PhonePhoto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    var bundleFromGalleryToImport = MutableLiveData<Bundle>()
    var importProgress = MutableLiveData(0)
    var importAmount = MutableLiveData(0)
    var importDone = MutableLiveData(false)

    fun saveGalleries(dmi: DeviceImageManager) {
        viewModelScope.launch {
            Log.i("Gallery Import", "Selected: ${dmi.selected}")
            var total: MutableList<PhonePhoto> = mutableListOf()

            // Count total amount
            for (album in dmi.albumCache) {
                if (album == null) continue
                if (!dmi.selected.any { pair -> pair.second == album.id }) {
                    Log.i("Gallery Import", "Skip gallery ${album.name} (${album.id})")
                    continue
                }

                for (photo in album.albumPhotos!!) {
                    total.add(photo)
                }
            }

            importAmount.postValue(total.size)

            var albumImportCount = 0

            total.chunked(5).forEach { chunk ->
                delay(500)

                Log.i("Import", "Chunk: $chunk")

                for (photo in chunk) {
                    Log.i("Gallery Import", "${photo.photoUri}")
                }
                Log.i("Gallery Import", "End chunk")

                albumImportCount += chunk.size
                importProgress.postValue(albumImportCount)

                //binding.progressLabel.text = getString(R.string.import_progress, (chunkCount * 50).toString(), album.albumPhotos?.size.toString())
            }

            importDone.postValue(true)
        }
    }

}
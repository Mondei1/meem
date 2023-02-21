package dev.klier.meem

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dev.klier.meem.databinding.FragmentSetupGalleryBinding
import dev.klier.meem.manager.DeviceImageManager
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetupGalleryFragment : Fragment() {

    data class Album(
        val uri: Uri,
        val name: String,
        val size: Int
    )
    val albumList = mutableListOf<Album>()

    private var _binding: FragmentSetupGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupGalleryBinding.inflate(inflater, container, false)

        val photos = DeviceImageManager.getInstance()
        context?.let {
            val albums = photos.getPhoneAlbums(it)

            Log.i("Gallery fragment", "Finished scan!")
            albums?.forEach { album ->
                Log.i("Gallery fragment", "Found: ${album?.name} (${album?.albumPhotos?.size})")
            }

            binding.galleryView.adapter =  GalleryAdapter(albums!!, Vector())
            binding.galleryView.layoutManager = GridLayoutManager(context, 3)
            binding.galleryView.isNestedScrollingEnabled = false
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
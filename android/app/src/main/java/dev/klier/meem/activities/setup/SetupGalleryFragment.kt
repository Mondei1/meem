package dev.klier.meem.activities.setup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dev.klier.meem.GalleryAdapter
import dev.klier.meem.R
import dev.klier.meem.databinding.FragmentSetupGalleryBinding
import dev.klier.meem.manager.DeviceImageManager
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetupGalleryFragment : Fragment() {

    private var _binding: FragmentSetupGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var dmi: DeviceImageManager = DeviceImageManager.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetupGalleryBinding.inflate(inflater, container, false)

        dmi = DeviceImageManager.getInstance()
        context?.let {
            dmi.populateCache(requireContext())

            Log.i("Gallery fragment", "Finished scan!")
            dmi.albumCache.forEach { album ->
                Log.i("Gallery fragment", "Found: ${album?.name} (${album?.albumPhotos?.size})")
            }

            val selected: MutableList<Pair<Int, Int>> = Vector()
            binding.galleryView.adapter = GalleryAdapter(dmi, selected)
            binding.galleryView.layoutManager = GridLayoutManager(context, 3)
            binding.galleryView.isNestedScrollingEnabled = false

            binding.albumNext.setOnClickListener {
                Log.i("Setup Gallery", selected.joinToString(", "))
                dmi.selected = selected

                val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
                val data = Bundle().apply { putSerializable("dmi", dmi) }
                viewModel.bundleFromGalleryToImport = MutableLiveData(data)

                findNavController().navigate(R.id.Gallery_To_Import)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
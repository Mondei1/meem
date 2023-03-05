package dev.klier.meem.activities.setup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dev.klier.meem.R
import dev.klier.meem.activities.main.MainActivity
import dev.klier.meem.databinding.FragmentSetupImportBinding
import dev.klier.meem.manager.DeviceImageManager
import dev.klier.meem.manager.IndexManager
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 * Use the [SetupImportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupImportFragment : Fragment() {
    private var viewModel: SharedViewModel? = null

    private var _binding: FragmentSetupImportBinding? = null
    private var indexManager: IndexManager? = null
    private var deviceImageManager: DeviceImageManager? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupImportBinding.inflate(inflater, container, false)
        indexManager = IndexManager(requireContext())
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        viewModel!!.bundleFromGalleryToImport.observe(viewLifecycleOwner) {
            deviceImageManager = it.getSerializable("dmi", DeviceImageManager::class.java)

            viewModel!!.saveGalleries(deviceImageManager!!)
        }

        viewModel!!.importProgress.observe(viewLifecycleOwner) {
            binding.progressLabel.text = getString(
                R.string.import_progress,
                viewModel?.importProgress?.value.toString(),
                viewModel?.importAmount?.value.toString()
            )
            binding.progressBar.progress = viewModel?.importProgress?.value!!
        }

        viewModel!!.importAmount.observe(viewLifecycleOwner) {
            binding.progressLabel.text = getString(
                R.string.import_progress,
                viewModel?.importProgress?.value.toString(),
                viewModel?.importProgress?.value.toString()
            )
            binding.progressBar.max = viewModel?.importAmount?.value!!
        }

        // As soon as the process is done, we mark the app state as "set up" and start the main
        // activity.
        viewModel!!.importDone.observe(viewLifecycleOwner) {
            if (it) {
                val prefs = requireContext().getSharedPreferences(requireContext().applicationInfo.packageName, AppCompatActivity.MODE_PRIVATE)
                with (prefs.edit()) {
                    putInt("last_version", 1)
                    apply()
                }

                this.startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }

        binding.progressLabel.text = getString(R.string.import_progress, "0", "0")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package dev.klier.meem.activities.setup

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import dev.klier.meem.R
import dev.klier.meem.databinding.FragmentSetupOperationModeBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SetupOperationModeFragment : Fragment() {

    private var _binding: FragmentSetupOperationModeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupOperationModeBinding.inflate(inflater, container, false)
        binding.toggleButton.check(R.id.local)
        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (checkedId == R.id.local && isChecked) {
                Log.i("Operation mode setup", "Local mode toggled")
            } else {
                Log.i("Operation mode setup", "Remote mode toggled")
            }
        }

        binding.operationModeNextButton.setOnClickListener {
            val targetPermission: String = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            } else {
                android.Manifest.permission.READ_MEDIA_IMAGES
            }

            if (requireContext().checkCallingOrSelfPermission(targetPermission) == PackageManager.PERMISSION_GRANTED) {
                findNavController().navigate(R.id.skipPermission)
            } else {
                findNavController().navigate(R.id.Operation_to_Permission)
            }
        }

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
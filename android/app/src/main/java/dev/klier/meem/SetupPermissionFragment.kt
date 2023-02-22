package dev.klier.meem

import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dev.klier.meem.databinding.FragmentSetupPermissionBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SetupPermissionFragment : Fragment() {

    private var _binding: FragmentSetupPermissionBinding? = null
    private var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(context, R.string.permissions_granted, Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.Permission_to_Gallery)
                } else {
                    Toast.makeText(context, R.string.permission_declined, Toast.LENGTH_LONG).show()
                }
            }

        _binding = FragmentSetupPermissionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonGrant.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher?.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                requestPermissionLauncher?.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
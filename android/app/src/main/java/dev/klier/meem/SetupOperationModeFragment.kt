package dev.klier.meem

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
            findNavController().navigate(R.id.Operation_to_Permission)
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
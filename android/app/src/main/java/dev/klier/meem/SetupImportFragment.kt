package dev.klier.meem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.klier.meem.databinding.FragmentSetupImportBinding
/**
 * A simple [Fragment] subclass.
 * Use the [SetupImportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupImportFragment : Fragment() {
    private var _binding: FragmentSetupImportBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupImportBinding.inflate(inflater, container, false)

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
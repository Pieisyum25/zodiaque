package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignListBinding

class SignListFragment : Fragment() {

    private var _binding: FragmentSignListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignListBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configure Views in here, as it ensures they have been fully created.
     * Prevents initialization errors/crashes.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Always do this to prevent Fragment memory leak.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jumpstopstudios.zodiaque.databinding.FragmentSignDetailBinding
import com.jumpstopstudios.zodiaque.model.MainViewModel
import com.jumpstopstudios.zodiaque.recyclerview.SiteAdapter
import java.util.*

class SignDetailFragment : Fragment() {

    private var _binding: FragmentSignDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val args: SignDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signDetailRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SiteAdapter(context, viewModel.horoscope, viewLifecycleOwner)
        }

        viewModel.apply {
            generateHoroscope(args.sign.name.lowercase(Locale.getDefault()))
            status.observe(viewLifecycleOwner) { status ->
                binding.signDetailStatus.text = status
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
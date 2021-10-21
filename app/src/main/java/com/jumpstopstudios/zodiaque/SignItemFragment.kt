package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignItemBinding
import com.jumpstopstudios.zodiaque.model.Sign

class SignItemFragment : Fragment() {

    companion object {

        fun newInstance(sign: Sign): SignItemFragment {
            val fragment = SignItemFragment()

            val args = Bundle()
            args.putString("name", sign.name)
            args.putInt("image_res_id", sign.imageResId)
            args.putString("dates_range", sign.datesRange)
            fragment.arguments = args

            return fragment
        }
    }

    private var _binding: FragmentSignItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signItemHeader.text = arguments?.getString("name")
        binding.signItemFooter.text = arguments?.getString("dates_range")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignItemBinding

class SignItemFragment : Fragment() {

    companion object {

        fun newInstance(sign: String): SignItemFragment {
            val fragment = SignItemFragment()

            val args = Bundle()
            args.putString("sign", sign)
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

        binding.signItemTitle.text = arguments?.getString("sign")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jumpstopstudios.zodiaque.databinding.FragmentSignItemBinding
import com.jumpstopstudios.zodiaque.model.Sign

class SignItemFragment : Fragment() {

    companion object {

        fun newInstance(sign: Sign): SignItemFragment {
            val fragment = SignItemFragment()

            val args = Bundle()
            args.putParcelable("sign", sign)
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

        val sign = arguments?.getParcelable<Sign>("sign")
        binding.signItemHeader.text = sign?.name
        binding.signItemFooter.text = sign?.datesRange

        binding.signItemCard.setOnClickListener {
            Log.d(TAG, "Started Item onClick")
            val action = SignListFragmentDirections.actionSignListFragmentToSignDetailFragment(sign!!)
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
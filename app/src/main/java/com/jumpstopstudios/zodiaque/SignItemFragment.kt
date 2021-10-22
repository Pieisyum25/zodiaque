package com.jumpstopstudios.zodiaque

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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

    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignItemBinding.inflate(inflater, container, false)
        viewPager = activity?.findViewById(R.id.sign_list_viewpager)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize sign info:
        val sign = arguments?.getParcelable<Sign>("sign")!!
        binding.signItemHeader.text = sign.name
        binding.signItemImage.setImageResource(sign.imageResId)
        binding.signItemFooter.text = sign.datesRange

        // Get screen horizontal centre value:
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenCentre = displayMetrics.widthPixels / 2

        binding.signItemContent.setOnClickListener {
            Log.d(TAG, "Started Item onClick")

            val page = binding.root.parent as FrameLayout
            val position = (viewPager[0] as RecyclerView).getChildAdapterPosition(page)

            // If in centre of screen, navigate to detail fragment:
            if (page.left < screenCentre && page.right > screenCentre){
                val action = SignListFragmentDirections.actionSignListFragmentToSignDetailFragment(sign!!)
                view.findNavController().navigate(action)
            }
            // Else, bring it to the centre:
            else {
                viewPager.setCurrentItem(position, true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
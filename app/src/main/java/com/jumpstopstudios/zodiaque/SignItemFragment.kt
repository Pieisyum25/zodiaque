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
import kotlin.properties.Delegates

class SignItemFragment : Fragment() {

    companion object {

        fun newInstance(viewPagerPosition: Int, sign: Sign): SignItemFragment {
            val fragment = SignItemFragment()

            val args = Bundle()
            args.putInt("viewpager_position", viewPagerPosition)
            args.putParcelable("sign", sign)
            fragment.arguments = args

            return fragment
        }
    }

    private var _binding: FragmentSignItemBinding? = null
    private val binding get() = _binding!!

    private var _viewPagerPosition by Delegates.notNull<Int>()
    val viewPagerPosition get() = _viewPagerPosition

    private var _position by Delegates.notNull<Int>()
    val position get() = _position


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

        // Initialize sign info:
        val sign = arguments?.getParcelable<Sign>("sign")!!
        binding.apply{
            signItemHeader.text = sign.name
            signItemImage.setImageResource(sign.imageResId)
            signItemFooter.text = sign.datesRange
        }
        _position = sign.position
        _viewPagerPosition = arguments?.getInt("viewpager_position")!!

        // Get screen horizontal centre value:
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenCentre = displayMetrics.widthPixels / 2

        binding.signItemContent.setOnClickListener {
            Log.d(TAG, "Started Item onClick")

            val page = binding.root.parent as FrameLayout
            val viewPager = activity?.findViewById<ViewPager2>(R.id.sign_list_viewpager)!!
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
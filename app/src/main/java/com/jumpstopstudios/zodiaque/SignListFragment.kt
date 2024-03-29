package com.jumpstopstudios.zodiaque

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignListBinding
import com.jumpstopstudios.zodiaque.model.SignItemDatasource
import com.jumpstopstudios.zodiaque.sign.SignPageChangeCallback
import com.jumpstopstudios.zodiaque.sign.SignPageTransformer
import com.jumpstopstudios.zodiaque.sign.SignPageWidthItemDecoration
import com.jumpstopstudios.zodiaque.sign.SignPagerAdapter

class SignListFragment : Fragment() {

    private var _binding: FragmentSignListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignListBinding.inflate(inflater, container, false)
        Log.d(TAG, "Finished List onCreateView")
        return binding.root
    }

    /**
     * Configure Views in here, as it ensures they have been fully created.
     * Prevents initialization errors/crashes.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Started List onViewCreate")

        // Hide collapsing toolbar:
        (activity as MainActivity).binding.collapsingToolbarContent.visibility = View.GONE

        // If orientation is landscape:
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.signPrompt.visibility = View.GONE // hide sign prompt
        }

        // Looping animations:
        val blinkAnimation = AnimationUtils.loadAnimation(context, R.anim.blink);
        binding.zodiacCircle.zodiacCircle.startAnimation(blinkAnimation)
        val bobAnimation = AnimationUtils.loadAnimation(context, R.anim.bob);
        binding.signListViewpager.startAnimation(bobAnimation)

        binding.signListViewpager.apply {
            Log.d(TAG, "Started List apply")
            val signs = SignItemDatasource.loadSigns(context)
            Log.d(TAG, "Finished List datasource load")
            val pageCount = signs.size
            val paddingPageCount = 2

            offscreenPageLimit = paddingPageCount - 1

            // Infinitely loop pages:
            adapter = SignPagerAdapter(this@SignListFragment.requireActivity(), signs, paddingPageCount)
            if (currentItem == 0) setCurrentItem(paddingPageCount, false)

            // Transform and animate pages:
            val callback = SignPageChangeCallback()
            registerOnPageChangeCallback(callback)
            setPageTransformer(SignPageTransformer(binding, pageCount, paddingPageCount, callback))
            addItemDecoration(SignPageWidthItemDecoration())

            Log.d(TAG, "Finished List apply")
        }
        Log.d(TAG, "Finished List onViewCreate")
    }

    /**
     * Always do this to prevent Fragment memory leak.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
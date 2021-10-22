package com.jumpstopstudios.zodiaque

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignListBinding
import com.jumpstopstudios.zodiaque.model.SignItemDatasource
import com.jumpstopstudios.zodiaque.viewpager.SignPageChangeCallback
import com.jumpstopstudios.zodiaque.viewpager.SignPageTransformer
import com.jumpstopstudios.zodiaque.viewpager.SignPageWidthItemDecoration
import com.jumpstopstudios.zodiaque.viewpager.SignPagerAdapter

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

        binding.signListViewpager.apply {
            Log.d(TAG, "Started List apply")
            val signs = SignItemDatasource.loadSigns(context)
            Log.d(TAG, "Finished List datasource load")
            val pageCount = signs.size
            val paddingPageCount = 2

            offscreenPageLimit = paddingPageCount - 1

            // Infinitely loop pages:
            adapter = SignPagerAdapter(this@SignListFragment.requireActivity(), signs, paddingPageCount)
            setCurrentItem(paddingPageCount, false)
            val callback = SignPageChangeCallback(this, pageCount, paddingPageCount)
            registerOnPageChangeCallback(callback)

            // Transform and animate pages:
            setPageTransformer(SignPageTransformer(this, callback))
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
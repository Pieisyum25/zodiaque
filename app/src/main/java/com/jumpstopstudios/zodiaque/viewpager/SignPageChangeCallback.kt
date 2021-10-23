package com.jumpstopstudios.zodiaque.viewpager

import androidx.viewpager2.widget.ViewPager2
import com.jumpstopstudios.zodiaque.databinding.FragmentSignListBinding
import kotlin.properties.Delegates

class SignPageChangeCallback(
    binding: FragmentSignListBinding,
    private val pageCount: Int,
    private val paddingPageCount: Int
    ) : ViewPager2.OnPageChangeCallback(){

    private var viewPager = binding.signListViewpager

    private var prevOffset = 0.0f
    var goingLeft: Boolean by Delegates.notNull()
    var progress: Float by Delegates.notNull()


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

        // Determine swipe direction:
        goingLeft = prevOffset > positionOffset
        prevOffset = positionOffset

        // Determine swipe progress:
        progress = when (position) {
            position -> positionOffset
            position + 1 -> 1 - positionOffset
            position - 1 -> 1 - positionOffset
            else -> 0f
        }

        // When a fake/copy page reaches the centre, seamlessly switch to the real page.
        // (For infinite looping)
        if (positionOffset == 0f) {
            when {
                position < paddingPageCount -> viewPager.setCurrentItem(position + pageCount, false)
                position >= pageCount + paddingPageCount -> viewPager.setCurrentItem(position - pageCount, false)
            }
        }
    }
}
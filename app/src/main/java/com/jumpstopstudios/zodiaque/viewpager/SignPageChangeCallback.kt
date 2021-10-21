package com.jumpstopstudios.zodiaque.viewpager

import androidx.viewpager2.widget.ViewPager2
import kotlin.properties.Delegates

class SignPageChangeCallback(
    private val viewPager: ViewPager2,
    private val pageCount: Int,
    private val paddingPageCount: Int
    ) : ViewPager2.OnPageChangeCallback(){

    private var prevOffset = 0.0f
    var goingLeft: Boolean by Delegates.notNull()
    var progress: Float by Delegates.notNull()

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)

        // When a fake/copy page reaches the centre, seamlessly switch to the real page.
        // (For infinite looping)
        if (positionOffset <= 0.01f) {
            when {
                position < paddingPageCount -> viewPager.setCurrentItem(position + pageCount, false)
                position >= pageCount + paddingPageCount -> viewPager.setCurrentItem(position - pageCount, false)
            }
        }

        // Determine swipe direction and progress.
        // (For rotation animation)
        goingLeft = prevOffset > positionOffset
        prevOffset = positionOffset
        progress = when (position) {
            position -> positionOffset
            position + 1 -> 1 - positionOffset
            position - 1 -> 1 - positionOffset
            else -> 0f
        }
    }
}
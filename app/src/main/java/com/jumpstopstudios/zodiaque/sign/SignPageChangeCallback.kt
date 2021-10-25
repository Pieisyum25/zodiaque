package com.jumpstopstudios.zodiaque.sign

import androidx.viewpager2.widget.ViewPager2
import kotlin.properties.Delegates

class SignPageChangeCallback : ViewPager2.OnPageChangeCallback(){


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
    }
}
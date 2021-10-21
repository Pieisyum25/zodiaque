package com.jumpstopstudios.zodiaque.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jumpstopstudios.zodiaque.SignItemFragment

class SignPagerAdapter(
    fragmentActivity: FragmentActivity, 
    private val signArray: Array<String>,
    private val paddingPageCount: Int
) : FragmentStateAdapter(fragmentActivity){

    private val pageCount = signArray.size
    
    override fun getItemCount() = pageCount + (paddingPageCount * 2)

    override fun createFragment(position: Int): Fragment {
        var adjustedPosition = position
        when {
            position < paddingPageCount -> adjustedPosition += pageCount
            position >= pageCount + paddingPageCount -> adjustedPosition -= pageCount
        }
        adjustedPosition -= paddingPageCount
        return SignItemFragment.newInstance(signArray[adjustedPosition])
    }
}
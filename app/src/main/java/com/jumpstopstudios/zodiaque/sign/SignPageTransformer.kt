package com.jumpstopstudios.zodiaque.sign

import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.findFragment
import androidx.viewpager2.widget.ViewPager2
import com.jumpstopstudios.zodiaque.R
import com.jumpstopstudios.zodiaque.SignItemFragment
import com.jumpstopstudios.zodiaque.databinding.FragmentSignListBinding
import kotlin.math.abs

class SignPageTransformer(
    binding: FragmentSignListBinding,
    private val pageCount: Int,
    private val paddingPageCount: Int,
    private val pageChangeCallback: SignPageChangeCallback,
    private val pageTranslationFactorX: Double = 0.8,
    private val pageTranslationFactorY: Double = 0.15,
    private val pageTranslationYSymmetrical: Boolean = true,
    private val pageScaleFactor: Double = 0.75,
    private val pageAlphaFactor: Double = 0.75
    ) : ViewPager2.PageTransformer {

    private val viewPager = binding.signListViewpager
    private val zodiacCircle = binding.zodiacCircle


        override fun transformPage(page: View, position: Float){
            val absPosition = abs(position)
            val cardView = page.findViewById<CardView>(R.id.sign_item_card)
            val fragment = cardView.findFragment<SignItemFragment>()

            // Translate off-centre pages:
            // Pages further from the centre are translated more towards the centre.
            page.translationX = ((viewPager.width - cardView.height * pageTranslationFactorX) * -position).toFloat()
            if (pageTranslationYSymmetrical) page.translationY = (page.height * pageTranslationFactorY * -absPosition).toFloat() // bent diagonal
            else page.translationY = (page.height * pageTranslationFactorY * -position).toFloat() // full diagonal, cool effect

            // Scale off-centre pages:
            // Pages further from the centre are scaled more (with a limit of the offscreenPageScaleFactor).
            var scale = when {
                absPosition > 1 -> pageScaleFactor
                position < 0 -> (1 - pageScaleFactor) * position + 1
                else -> (pageScaleFactor - 1) * position + 1
            }
            val scaleFloat = scale.toFloat()
            page.scaleX = scaleFloat
            page.scaleY = scaleFloat

            // Fade off-centre pages:
            page.alpha = when {
                absPosition > 1 -> pageAlphaFactor.toFloat()
                else -> ((pageAlphaFactor - 1) * absPosition + 1).toFloat()
            }

            // Rotation animation:
            // Adapted from https://www.bornfight.com/blog/how-to-build-animated-lists-with-motionlayout-and-viewpager2/
            if (pageChangeCallback.goingLeft) ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(
                R.id.leftToRight)
            else ((page as ViewGroup).getChildAt(0) as MotionLayout).setTransition(R.id.rightToLeft)
            (page.getChildAt(0) as MotionLayout).progress = pageChangeCallback.progress


            // Rotate background image:
            val index = fragment.position
            if (index == viewPager.currentItem - paddingPageCount){
                val percent = (index - position) / pageCount
                zodiacCircle.rotation = -percent * 360
            }

            // When a fake/copy page reaches the centre, seamlessly switch to the real page.
            // (For infinite looping)
            val viewPagerIndex = fragment.viewPagerPosition
            if (viewPagerIndex == viewPager.currentItem) {
                //Log.d(TAG, position.toString())
                if (position == 0.0f) {
                    when {
                        viewPagerIndex < paddingPageCount -> viewPager.setCurrentItem(viewPagerIndex + pageCount, false)
                        viewPagerIndex >= pageCount + paddingPageCount -> viewPager.setCurrentItem(viewPagerIndex - pageCount, false)
                    }
                }
            }
        }
}
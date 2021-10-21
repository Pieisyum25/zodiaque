package com.jumpstopstudios.zodiaque.viewpager

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager2.widget.ViewPager2
import com.jumpstopstudios.zodiaque.R
import kotlin.math.abs

class SignPageTransformer(
    private val pageChangeCallback: SignPageChangeCallback,
    private val pageTranslationFactorX: Double = 0.45,
    private val pageTranslationFactorY: Double = 0.15,
    private val pageTranslationYSymmetrical: Boolean = true,
    private val pageScaleFactor: Double = 0.75,
    private val pageAlphaFactor: Double = 0.5
    ) : ViewPager2.PageTransformer {

        override fun transformPage(page: View, position: Float){
            val absPosition = abs(position)

            // Translate off-centre pages:
            // Pages further from the centre are translated more towards the centre.
            page.translationX = (page.width * pageTranslationFactorX * -position).toFloat()
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
        }
}
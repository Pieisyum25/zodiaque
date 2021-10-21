package com.jumpstopstudios.zodiaque.viewpager

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SignPageWidthItemDecoration(
    private val pageWidthFactor: Double = 0.8
) : RecyclerView.ItemDecoration() {

    /**
     * The margin value to set on each side of the page to make it thinner.
     */
    private var horizontalMargin = -1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // If margin width not calculated, calculate it from the View width:
        if (horizontalMargin == -1){
            if (view.width == 0) fixLayoutSize(view, parent)
            horizontalMargin = ((1 - pageWidthFactor) / 2 * view.width).toInt()
        }
        // Set the page's margins to make the page thinner:
        outRect.right = horizontalMargin
        outRect.left = horizontalMargin
    }

    /**
     * Sets view's size if it hasn't been initialized yet.
     * Taken from https://stackoverflow.com/a/53848159
     */
    private fun fixLayoutSize(view: View, parent: ViewGroup) {
        if (view.layoutParams == null) {
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
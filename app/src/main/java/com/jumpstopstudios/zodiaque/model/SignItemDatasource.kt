package com.jumpstopstudios.zodiaque.model

import android.content.Context
import android.util.Log
import com.jumpstopstudios.zodiaque.R

object SignItemDatasource {

    private val signDateRangesData: Array<IntArray> = arrayOf(
        intArrayOf(3, 21, 4, 19),
        intArrayOf(4, 20, 5, 20),
        intArrayOf(5, 21, 6, 20),
        intArrayOf(6, 21, 7, 22),
        intArrayOf(7, 23, 8, 22),
        intArrayOf(8, 23, 9, 22),
        intArrayOf(9, 23, 10, 22),
        intArrayOf(10, 23, 11, 21),
        intArrayOf(11, 22, 12, 21),
        intArrayOf(12, 22, 1, 19),
        intArrayOf(1, 20, 2, 18),
        intArrayOf(2, 19, 3, 20)
    )

    fun loadSigns(context: Context): List<Sign> {
        val signs = mutableListOf<Sign>()
        val res = context.resources
        val names = res.getStringArray(R.array.signs)
        val months = res.getStringArray(R.array.months)

        for (index in 0..11) {
            val name = names[index]
            val imageResId = res.getIdentifier("sign$index", "drawable", context.packageName)

            val dateRangeData = signDateRangesData[index]
            val startMonth = months[dateRangeData[0]]
            val startDay = dateRangeData[1]
            val endMonth = months[dateRangeData[2]]
            val endDay = dateRangeData[3]
            val dateRange = res.getString(R.string.sign_dates_range, startMonth, startDay, endMonth, endDay)

            signs.add(Sign(name, imageResId, dateRange, index))
        }
        return signs
    }
}
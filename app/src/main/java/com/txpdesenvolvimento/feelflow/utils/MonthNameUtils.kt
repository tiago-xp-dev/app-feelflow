package com.txpdesenvolvimento.feelflow.utils

import android.content.Context
import com.txpdesenvolvimento.feelflow.R

class MonthNameUtils {

    companion object {
        @JvmStatic fun getMonthName(calendarId: Int, context: Context): String {
            when(calendarId){
                0 -> return context.getString(R.string.month_name_january)
                1 -> return context.getString(R.string.month_name_february)
                2 -> return context.getString(R.string.month_name_march)
                3 -> return context.getString(R.string.month_name_april)
                4 -> return context.getString(R.string.month_name_may)
                5 -> return context.getString(R.string.month_name_june)
                6 -> return context.getString(R.string.month_name_july)
                7 -> return context.getString(R.string.month_name_august)
                8 -> return context.getString(R.string.month_name_september)
                9 -> return context.getString(R.string.month_name_october)
                10 -> return context.getString(R.string.month_name_november)
                11 -> return context.getString(R.string.month_name_december)
                else -> return "Err"
            }
        }
    }
}
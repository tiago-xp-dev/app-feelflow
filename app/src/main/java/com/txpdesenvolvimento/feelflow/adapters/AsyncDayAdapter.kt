package com.txpdesenvolvimento.feelflow.adapters


import AsyncCell
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.FrameLayout.LayoutParams
import android.widget.TableLayout
import androidx.core.view.marginLeft
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.ActivityCalendarDayBinding
import com.txpdesenvolvimento.feelflow.databinding.ActivityCalendarDayEmptyBinding
import com.txpdesenvolvimento.feelflow.models.BaseEntry
import com.txpdesenvolvimento.feelflow.models.Day
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton
import java.util.Calendar
import kotlin.math.roundToInt

class AsyncDayAdapter internal constructor(private val dataSet: Array<Day>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            Day.DayType.DAY.value -> DayItemViewHolder(DayItemCell(parent).apply { inflate() })
            Day.DayType.EMPTY.value -> EmptyItemViewHolder(EmptyItemCell(parent).apply { inflate() })
            else -> TODO("Fix")
        }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type.ordinal
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DayItemViewHolder -> setUpDayViewHolder(holder, position)
            is EmptyItemViewHolder -> setupEmptyViewHolder(holder, position)
        }
    }

    private fun setUpDayViewHolder(holder: DayItemViewHolder, position: Int) {
        (holder.itemView as DayItemCell).bindWhenInflated {
            dataSet[position].let { item ->
                val binding = holder.itemView.binding!!
                var dayContainer = binding.dayContainer

                val day = item.refDate!!.get(Calendar.DAY_OF_MONTH)
                val entries = item.entries

                val background = binding.viewBackground
                binding.txtVDay.text = day.toString()

                dayContainer.setOnClickListener {
                    NavControllerSingleton.getInstance().navigate(R.id.nav_entry_overview)
                }

                //background.visibility = VISIBLE
                if (entries.isNotEmpty()){
                    when (entries.first().rating) {
                        BaseEntry.Rating.AWFUL -> dayContainer.backgroundTintList = context.getColorStateList(R.color.rating_awesome)
                        BaseEntry.Rating.BAD -> dayContainer.backgroundTintList = context.getColorStateList(R.color.rating_bad)
                        BaseEntry.Rating.NEUTRAL -> dayContainer.backgroundTintList = context.getColorStateList(R.color.rating_neutral)
                        BaseEntry.Rating.GOOD -> dayContainer.backgroundTintList = context.getColorStateList(R.color.rating_good)
                        BaseEntry.Rating.AWESOME -> dayContainer.backgroundTintList = context.getColorStateList(R.color.rating_awesome)
                    }
                }else {
                    //background.visibility = INVISIBLE
                }
            }
        }
    }

    private fun setupEmptyViewHolder(holder: EmptyItemViewHolder, position: Int) {
        (holder.itemView as EmptyItemCell).bindWhenInflated {
            dataSet[position].let {}
        }
    }

    private inner class DayItemViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    private inner class EmptyItemViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    private inner class DayItemCell(viewGroup: ViewGroup) : AsyncCell(viewGroup) {
        var binding: ActivityCalendarDayBinding? = null
        override val layoutId = R.layout.activity_calendar_day
        override fun createDataBindingView(view: View): View? {
            binding = ActivityCalendarDayBinding.bind(view)
            return view.rootView
        }
    }

    private inner class EmptyItemCell(viewGroup: ViewGroup) : AsyncCell(viewGroup) {
        var binding: ActivityCalendarDayEmptyBinding? = null
        override val layoutId = R.layout.activity_calendar_day_empty
        override fun createDataBindingView(view: View): View? {
            binding = ActivityCalendarDayEmptyBinding.bind(view)
            return view.rootView
        }
    }
}
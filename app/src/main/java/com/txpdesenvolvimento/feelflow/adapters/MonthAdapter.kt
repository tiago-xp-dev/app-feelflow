package com.txpdesenvolvimento.feelflow.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.models.BaseEntry.Rating
import com.txpdesenvolvimento.feelflow.models.Day
import com.txpdesenvolvimento.feelflow.models.Day.DayType.DAY
import com.txpdesenvolvimento.feelflow.models.Day.DayType.EMPTY
import com.txpdesenvolvimento.feelflow.models.Entry
import com.txpdesenvolvimento.feelflow.models.Month
import com.txpdesenvolvimento.feelflow.utils.MonthNameUtils
import java.util.Calendar
import kotlin.random.Random

class MonthAdapter(private val dataSet: Array<Month>): RecyclerView.Adapter<MonthAdapter.ViewHolder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_calendar_month, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val year = dataSet[position].year
        val month = dataSet[position].month
        val context = holder.itemView.context!!

        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        val countDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val offsetFirstWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val tableCalendar = holder.container
        val calendarTitle = holder.title

        calendarTitle.text =
            MonthNameUtils.getMonthName(calendar.get(Calendar.MONTH), context)

        tableCalendar.layoutManager = GridLayoutManager(context, 7, VERTICAL, false)
        val days: MutableList<Day> = mutableListOf()
        if (tableCalendar.childCount == 0) {
            for (count in 1..<(countDays + offsetFirstWeek)){
                if (count < offsetFirstWeek) {
                    days.add(Day(null, EMPTY, listOf()))
                } else {
                    val refDate = Calendar.getInstance()
                    refDate.set(year, month, (count - offsetFirstWeek) + 1)

                    val mockRating = Random(seed = refDate.timeInMillis).nextInt(0, 5)
                    var mockEntries = listOf<Entry>()
                    if(mockRating < 5) {
                        mockEntries = listOf(Entry(0, refDate, Rating.fromInt(mockRating)))
                    }

                    days.add(Day(refDate, DAY, mockEntries))
                }
            }

            tableCalendar.adapter = AsyncDayAdapter(days.toTypedArray())
        }
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView
        val container: RecyclerView
        init{
            this.title = itemView.findViewById(R.id.txtVMonthName)
            this.container = itemView.findViewById(R.id.recViewDays)
        }
    }

    class LinearSpacesLineDecoration(spacing: Int): ItemDecoration() {

        private val spacing: Int
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if(parent.adapter != null
                && parent.getChildLayoutPosition(view) != parent.adapter!!.itemCount - 1){
                outRect.bottom = spacing
            }
        }
        init{
            this.spacing = spacing
        }
    }
}
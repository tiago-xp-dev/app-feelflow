package com.txpdesenvolvimento.feelflow.services.calendar

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import java.util.Calendar


class MonthAdapter : Adapter<MonthAdapter.ViewHolder>  {
    private var year : Int
    private var month: Int
    private var days : List<Day>
    private var context: Context

    constructor(year: Int, month: Int, days: List<Day>, context: Context) : super() {
        this.year = year
        this.month = month
        this.days = days
        this.context = context

        // Obtem todos os dias do mês
        var refDate = Calendar.getInstance()
        refDate.set(year, month, 1)

        var daysInMonth = refDate.getMaximum(Calendar.DAY_OF_MONTH)

        for(i in 1 until daysInMonth){
            val dayRefDate = Calendar.getInstance()
            dayRefDate.set(year, month, i)

            val day = Day(dayRefDate, emptyList())
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(0, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return days.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ViewHolder : RecyclerView.ViewHolder {

        constructor(itemView: View) : super(itemView){

        }
    }
}
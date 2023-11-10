package com.txpdesenvolvimento.feelflow.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.contains
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarDayBinding
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarMonthBinding
import com.txpdesenvolvimento.feelflow.utils.MonthNameUtils
import java.util.Calendar
import kotlin.math.roundToInt

class CalendarMonthFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarMonthFragment()
    }

    private var tableRowIds: ArrayList<Int> = arrayListOf()
    private var _binding: FragmentCalendarMonthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarMonthBinding.inflate(inflater, container, false)

        val bundle = requireArguments()

        val savedRowIds = savedInstanceState?.getIntegerArrayList("tableRowIds")
        if(savedRowIds != null){
            tableRowIds = savedRowIds
        }

        val year = bundle.getInt("year")
        val month = bundle.getInt("month")

        // Criando o Calendário responsável por desabstrair a Data.
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)

        // Ultimo dia do mês = Quantidade de Dias no Mês
        val countDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val countWeeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)
        val offsetFirstWeek = calendar.get(Calendar.DAY_OF_WEEK)

        var tableCalendar = binding.tbLWeeks
        var calendarTitle = binding.txtVMonthName

        calendarTitle.text = MonthNameUtils.getMonthName(calendar.get(Calendar.MONTH), requireContext())

        tableCalendar.dividerDrawable = context?.getDrawable(R.drawable.calendar_vertical_divider)
        tableCalendar.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)

        if(tableRowIds.isEmpty()){
            var row: TableRow = createRow(tableCalendar)
            for (count in 1..(countDays+offsetFirstWeek)-1){

            if(count < offsetFirstWeek){
                createDayFrag(DayType.EMPTY, row.id)
            }else{
                var dayBundle = Bundle()
                dayBundle.putInt("day", (count-offsetFirstWeek)+1)
                createDayFrag(DayType.DAY, row.id, dayBundle)
            }

                if (count % 7 == 0) {
                    row = createRow(tableCalendar)
                }
            }
        }else{
            tableRowIds.forEach {
                createRow(tableCalendar, it)
            }
        }

        binding.root.post(Runnable {
            val tableWidth = binding.root.findViewById<TableLayout>(R.id.tbLWeeks).measuredWidth - (resources.getDimension(R.dimen.calendar_day_margin) * 6)
            val dayDimension = (tableWidth / 7).roundToInt()

            childFragmentManager.fragments.forEach{
                var params = it.view?.layoutParams
                params?.width = dayDimension
                params?.height = dayDimension

                it.view?.setLayoutParams(params)
                it.view?.requestLayout()
            }
        })

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntegerArrayList("tableRowIds", tableRowIds)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()

        tableRowIds.forEach {
            if(binding.tbLWeeks.findViewById<TableRow>(it) == null) {
                createRow(binding.tbLWeeks, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createDayFrag(type: DayType, containerId: Int, bundle: Bundle? = null){
        if(type == DayType.DAY){
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val fragment = CalendarDayFragment()
            fragment.arguments = bundle
            fragmentTransaction.add(containerId, fragment)
            fragmentTransaction.commit()
        }else{
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val fragment = CalendarDayEmptyFragment()
            fragmentTransaction.add(containerId, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun createRow(table: TableLayout, id : Int = View.generateViewId()): TableRow{
        var row = TableRow(context)

        row.id = id
        row.dividerDrawable = context?.getDrawable(R.drawable.calendar_vertical_divider)
        row.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)

        table.addView(row)

        if(!tableRowIds.contains(id)) {
            tableRowIds.add(id)
        }
        return row
    }
    enum class DayType{
        EMPTY,
        DAY
    }
}
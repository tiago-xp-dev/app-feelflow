package com.txpdesenvolvimento.feelflow.ui.fragment

import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams
import android.widget.TableLayout
import android.widget.TableRow
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarDayBinding
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarMonthBinding
import com.txpdesenvolvimento.feelflow.utils.MonthNameUtils
import java.util.Calendar

class CalendarMonthFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarMonthFragment()
    }

    private lateinit var viewModel: CalendarMonthViewModel
    private lateinit var tableRows: List<TableRow>

    private var _binding: FragmentCalendarMonthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarMonthBinding.inflate(inflater, container, false)
        val fragmentManager = childFragmentManager

        tableRows = listOf()

        val bundle = requireArguments()

        val tableWidth = binding.root.findViewById<TableLayout>(R.id.tbLWeeks).measuredWidth - (resources.getDimension(R.dimen.calendar_day_margin) * 6)
        val dayDimension = 42 //tableWidth / 7

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

        var row : TableRow = createRow(tableCalendar)
        for (count in 1 ..  (countDays+offsetFirstWeek)-1){

            if(count < offsetFirstWeek){
                createDayFrag(DayType.EMPTY, row!!.id)
            }else{
                createDayFrag(DayType.DAY, row!!.id)
            }

            if(count % 7 == 0){
                row = createRow(tableCalendar)
            }
        }

        binding.root.post(Runnable {
            val tableWidth = binding.root.findViewById<TableLayout>(R.id.tbLWeeks).measuredWidth - (resources.getDimension(R.dimen.calendar_day_margin) * 6)
            val dayDimension = Math.round(tableWidth / 7)

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

    private fun createDayFrag(type: DayType, containerId: Int){
        if(type == DayType.DAY){
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val fragment = CalendarDayFragment()
            fragmentTransaction.add(containerId, fragment)
            fragmentTransaction.commit()
        }else{
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val fragment = CalendarDayEmptyFragment()
            fragmentTransaction.add(containerId, fragment)
            fragmentTransaction.commit()
        }
    }

    private fun createRow(table: TableLayout): TableRow{
        var row = TableRow(context)

        row.id = View.generateViewId()
        row.dividerDrawable = context?.getDrawable(R.drawable.calendar_vertical_divider)
        row.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE)
        table.addView(row)

        return row
    }
    enum class DayType{
        EMPTY,
        DAY
    }
}
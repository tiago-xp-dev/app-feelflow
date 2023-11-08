package com.txpdesenvolvimento.feelflow.ui.fragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import com.txpdesenvolvimento.feelflow.R
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

        tableRows = listOf()

        val bundle = requireArguments()

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

        var row = TableRow(context)
        row.dividerDrawable = context?.getDrawable(R.drawable.calendar_vertical_divider)

        for (count in 1 ..  (countDays+offsetFirstWeek)-1){
            if(count < offsetFirstWeek){
                var emptyDay = layoutInflater.inflate(R.layout.fragment_calendar_day_empty, null)
                row.addView(emptyDay)
            }else{
                var day = layoutInflater.inflate(R.layout.fragment_calendar_day, null)
                row.addView(day)
            }
            if(count > 0 && count % 7 == 0){
                tableCalendar.addView(row)
                row = TableRow(context)
                row.dividerDrawable = context?.getDrawable(R.drawable.calendar_vertical_divider)
            }else if(count == (countDays+offsetFirstWeek)-1){
                tableCalendar.addView(row)
            }
        }

        return binding.root
    }
}
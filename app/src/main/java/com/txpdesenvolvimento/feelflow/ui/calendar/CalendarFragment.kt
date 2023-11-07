package com.txpdesenvolvimento.feelflow.ui.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val table = binding.tableLayout

        for (i in 0 until  12 step 1){
            val row = TableRow(root.context)

            /*val v1 = layoutInflater.inflate(R.layout.fragment_year_in_pixel_day, null)
            val v2 = layoutInflater.inflate(R.layout.fragment_year_in_pixel_day, null)
            val v3 = layoutInflater.inflate(R.layout.fragment_year_in_pixel_day, null)
            val v4 = layoutInflater.inflate(R.layout.fragment_year_in_pixel_day, null)

            row.addView(v1)
            row.addView(v2)
            row.addView(v3)
            row.addView(v4)*/

            val cal = layoutInflater.inflate(R.layout.fragment_calendar_month, null)
            row.addView(cal)
            table.addView(row)
        }

        return root
    }
}
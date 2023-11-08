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
import androidx.core.util.rangeTo
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarBinding
import com.txpdesenvolvimento.feelflow.ui.fragment.CalendarMonthFragment

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
        val fragmentManager = childFragmentManager

        val viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root : View = binding.root

        val linearLayout = binding.container

        for (i in 0 until 12){
            var fBundle = Bundle()
            fBundle.putInt("year", 2023)
            fBundle.putInt("month", i)

            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = CalendarMonthFragment()
            fragment.arguments = fBundle

            fragmentTransaction.add(R.id.container, fragment)
            fragmentTransaction.commit()

            //val cal = layoutInflater.inflate(R.layout.fragment_calendar_month, null)
            //linearLayout.addView(cal)
        }

        return root
    }
}
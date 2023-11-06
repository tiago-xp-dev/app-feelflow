package com.txpdesenvolvimento.feelflow.ui.calendar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarBinding
import com.txpdesenvolvimento.feelflow.databinding.FragmentHomeBinding

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

        val field: TextView = binding.txtYear

        viewModel.year.observe(viewLifecycleOwner) {
            field.text = it.toString()
        }

        return root
        //return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}
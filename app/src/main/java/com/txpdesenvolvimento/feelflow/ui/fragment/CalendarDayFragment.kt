package com.txpdesenvolvimento.feelflow.ui.fragment

import android.app.Notification
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarDayBinding

class CalendarDayFragment : Fragment() {

    private var _binding: FragmentCalendarDayBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarDayBinding.inflate(inflater, container, false)

        val buttonDay : Button = binding.btnDay
        val dayNumber = arguments?.getInt("day")
        buttonDay.text = dayNumber.toString()

        buttonDay.setOnClickListener {

        }

        return binding.root
    }
}
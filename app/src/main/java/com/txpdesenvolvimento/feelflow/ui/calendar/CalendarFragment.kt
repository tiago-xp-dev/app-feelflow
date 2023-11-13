package com.txpdesenvolvimento.feelflow.ui.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.txpdesenvolvimento.feelflow.R
import com.txpdesenvolvimento.feelflow.databinding.FragmentCalendarBinding
import com.txpdesenvolvimento.feelflow.models.Month
import com.txpdesenvolvimento.feelflow.adapters.MonthAdapter

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root : View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val months = arrayListOf<Month>()
        if(childFragmentManager.fragments.isEmpty()){
            for (i in 0 until 12){
                months.add(Month(2023,i, arrayListOf()))
            }
        }

        var calendarSpacing = resources.getDimension(R.dimen.calendar_bottom_spacing)
        binding.container.addItemDecoration(MonthAdapter.LinearSpacesLineDecoration(calendarSpacing.toInt()))
        binding.container.layoutManager = LinearLayoutManager(context)
        binding.container.adapter = MonthAdapter(months.toTypedArray())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
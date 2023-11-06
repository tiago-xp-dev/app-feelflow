package com.txpdesenvolvimento.feelflow.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar
import java.util.Date

class CalendarViewModel : ViewModel() {
    private val _year = MutableLiveData<Int>().apply {
        value = Calendar.getInstance().get(Calendar.YEAR);
    }
    val year: LiveData<Int> = _year
}
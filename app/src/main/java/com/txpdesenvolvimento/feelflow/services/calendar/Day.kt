package com.txpdesenvolvimento.feelflow.services.calendar

import com.txpdesenvolvimento.feelflow.services.entries.Entry
import java.util.Calendar

class Day {
    private var entries : List<Entry>
    private var refDate : Calendar

    constructor(refDate: Calendar, entries: List<Entry>) {
        this.refDate = refDate
        this.entries = entries
    }
    fun getEntries(): List<Entry>{
        return entries
    }
    fun setEntries(entries:List<Entry>){
        this.entries = entries
    }
}
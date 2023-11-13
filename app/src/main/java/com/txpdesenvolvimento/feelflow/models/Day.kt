package com.txpdesenvolvimento.feelflow.models

import java.util.Calendar

class Day (refDate: Calendar?, type: DayType, entries: List<Entry>) {

    private var _refDate : Calendar?
    var refDate: Calendar?
        get() = _refDate
        set(value) {
            this._refDate = value
        }

    private var _type : DayType
    var type: DayType
        get() = _type
        set(value) {
            _type = value
        }

    private var _entries : List<Entry>
    var entries: List<Entry>
        get() = _entries
        set(value) {
            _entries = value
        }

    init{
        this._refDate = refDate
        this._type = type
        this._entries = entries
    }

    enum class DayType(val value: Int){
        EMPTY(0),
        DAY(1);
    }
}
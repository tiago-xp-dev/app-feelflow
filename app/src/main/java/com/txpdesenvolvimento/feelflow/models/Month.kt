package com.txpdesenvolvimento.feelflow.models

class Month (year:Int, month: Int, entries: ArrayList<BaseEntry>){

    private var _year : Int
    var year: Int
        set(value) {
            _year = value
        }
        get() = _year

    private var _month : Int
    var month: Int
        get() = _month
        set(value) {
            _month = value
        }

    private var _entries = arrayListOf<BaseEntry>()
    var entries: ArrayList<BaseEntry>
        get() = _entries
        set(value){
            _entries = value
        }

    init {
        this._year = year
        this._month = month
        this._entries = entries
    }

}
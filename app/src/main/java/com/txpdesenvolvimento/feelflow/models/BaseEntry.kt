package com.txpdesenvolvimento.feelflow.models

import java.util.Calendar

open class BaseEntry(id: Int, refDate: Calendar, rating: Rating) {

    private var _id : Int
    var id : Int
        get() = _id
        set(value) {
            this._id = value
        }
    private var _refDate: Calendar
    var refDate : Calendar
        get() = _refDate
        set(value) {
            _refDate = value
        }

    private var _rating: Rating
    var rating : Rating
        get() = _rating
        set(value) {
            _rating = value
        }

    init{
        this._id = id
        this._refDate = refDate
        this._rating = rating
    }

    enum class Rating(val value: Int){
        AWFUL(0),
        BAD(1),
        NEUTRAL(2),
        GOOD(3),
        AWESOME(4);

        companion object {
            fun fromInt(value: Int) = entries.first { it.value == value }
        }
    }
}
package com.txpdesenvolvimento.feelflow.models.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

class DayDto {
    @SerializedName("id")
     var id : Int = -1
    @SerializedName("user_id")
    var userId : Int = -1
    @SerializedName("reference_date")
    var referenceDate : Date = Date()
}
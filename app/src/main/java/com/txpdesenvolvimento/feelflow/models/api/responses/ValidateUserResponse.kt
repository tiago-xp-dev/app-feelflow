package com.txpdesenvolvimento.feelflow.models.api.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ValidateUserResponse(token: String?, status: Boolean, message: String) {
    @SerializedName("token")
    @Expose
    private var _token : String?
    var token : String?

        get() = _token
        set(value) {
            _token = value
        }

    @SerializedName("status")
    @Expose
    private var _status : Boolean
    var status : Boolean

        get() = _status
        set(value) {
            _status = value
        }

    @SerializedName("message")
    @Expose
    private var _message : String
    var message : String

        get() = _message
        set(value) {
            _message = value
        }

    init{
        this._status = status
        this._message = message
        this._token = token
    }
}
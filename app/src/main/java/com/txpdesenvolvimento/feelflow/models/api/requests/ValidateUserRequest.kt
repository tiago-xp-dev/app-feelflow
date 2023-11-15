package com.txpdesenvolvimento.feelflow.models.api.requests

import com.google.gson.annotations.SerializedName

class ValidateUserRequest(password: String, email: String) {

    @SerializedName("password")
    private var _password : String
    var password : String

        get() = _password
        set(value) {
            _password = value
        }

    @SerializedName("email")
    private var _email : String
    var email : String
        get() = _email
        set(value) {
            _email = value
        }

    init {
        this._password = password
        this._email = email
    }
}
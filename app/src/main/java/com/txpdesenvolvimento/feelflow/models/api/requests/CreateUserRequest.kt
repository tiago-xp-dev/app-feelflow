package com.txpdesenvolvimento.feelflow.models.api.requests

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

class CreateUserRequest(userName:String, password: String, email: String) {
    @SerializedName("user_name")
    private var _userName : String
    var userName : String
        get() = _userName
        set(value) {
            _userName = value
        }

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
        this._userName = userName
        this._password = password
        this._email = email
    }
}
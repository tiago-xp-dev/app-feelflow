package com.txpdesenvolvimento.feelflow.services.api

import com.txpdesenvolvimento.feelflow.models.api.requests.CreateUserRequest
import com.txpdesenvolvimento.feelflow.models.api.requests.ValidateUserRequest
import com.txpdesenvolvimento.feelflow.models.api.responses.ValidateUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UserServices {
    @POST("user/create")
    fun createUser(@Body body : CreateUserRequest): Call<Void>

    @POST("user/validate")
    fun validateUser(@Body body: ValidateUserRequest): Call<ValidateUserResponse>
}
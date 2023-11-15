package com.txpdesenvolvimento.feelflow.services.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServices {
    companion object{
        @JvmStatic val apiUrl : String = "http://192.168.0.225:8000"

        @JvmStatic private var instance : Retrofit? = null
        @JvmStatic fun getInstance() : Retrofit {
            if(instance == null){
                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(apiUrl).build()
            }

            return instance!!
        }
    }
}
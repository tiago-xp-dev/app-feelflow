package com.txpdesenvolvimento.feelflow.utils

import android.content.Context
import android.content.SharedPreferences

class JWTTokenUtils(context: Context) {
    private val tokenKey = "feelflow_api_token"
    private val tokenName = "token"
    private val _context: Context

    init{
        this._context = context
    }

    fun storeToken(token: String){
    preferences.edit().putString(tokenName, token).apply()
    }

    fun retrieveToken(): String? {
        if(tokenExists)
            return preferences.getString(tokenName, null)!!
        return null
    }

    fun clearToken(){
        if(tokenExists)
            preferences.edit().clear().apply()
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(tokenKey, Context.MODE_PRIVATE)
    private val tokenExists: Boolean = preferences.contains(tokenName)

}
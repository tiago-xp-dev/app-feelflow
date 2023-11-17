package com.txpdesenvolvimento.feelflow.services.api

import com.txpdesenvolvimento.feelflow.services.api.interfaces.UserServices

class UsersApi {
    companion object{
        @JvmStatic private var instance : UserServices? = null
        @JvmStatic fun services() : UserServices {
            if(instance == null){
                instance = ApiServices.getInstance().create(UserServices::class.java)
            }

            return instance!!
        }
    }
}
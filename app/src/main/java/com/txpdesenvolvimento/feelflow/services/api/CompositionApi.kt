package com.txpdesenvolvimento.feelflow.services.api.interfaces

import com.txpdesenvolvimento.feelflow.services.api.ApiServices

class CompositionApi {
    companion object{
        @JvmStatic private var instance : CompositionServices? = null
        @JvmStatic fun services() : CompositionServices {
            if(instance == null){
                instance = ApiServices.getInstance().create(CompositionServices::class.java)
            }

            return instance!!
        }
    }
}
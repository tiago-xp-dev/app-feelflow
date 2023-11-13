package com.txpdesenvolvimento.feelflow.utils

import androidx.navigation.NavController

class NavControllerSingleton(navController: NavController) {

    companion object{
        @JvmStatic private var instance : NavController? = null

        @JvmStatic fun getInstance(): NavController{
            return instance!!
        }
    }
    init {
        instance = navController
    }
}
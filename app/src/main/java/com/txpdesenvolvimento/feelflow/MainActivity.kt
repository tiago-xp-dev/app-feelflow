package com.txpdesenvolvimento.feelflow

import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.txpdesenvolvimento.feelflow.databinding.ActivityMainBinding
import com.txpdesenvolvimento.feelflow.ui.calendar.CalendarFragmentDirections
import com.txpdesenvolvimento.feelflow.utils.JWTTokenUtils
import com.txpdesenvolvimento.feelflow.utils.NavControllerSingleton

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val containerVideo = binding.appBarMain.content.containerVideo
        val toolbar = binding.appBarMain.toolbar
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_year_in_pixels), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        NavControllerSingleton(navController)

        val validateSignInOrSignup = NavController.OnDestinationChangedListener { controller, destination, _ ->
            if(destination.id == R.id.nav_sign_in || destination.id == R.id.nav_sign_up){
                val params = containerVideo.layoutParams as MarginLayoutParams

                if(params.topMargin > 0){
                     params.topMargin = 0
                     containerVideo.layoutParams = params
                     containerVideo.requestLayout()

                    toolbar.visibility = GONE
                    drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                }
            }else{
                val params = containerVideo.layoutParams as MarginLayoutParams
                if(params.topMargin == 0){
                    params.topMargin = resources.getDimension(R.dimen.action_bar_size).toInt()
                    containerVideo.layoutParams = params
                    containerVideo.requestLayout()

                    toolbar.visibility = VISIBLE
                    drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                }
            }
        }

        // Se está logado, procede para a Tela de Calendário.
        if(!JWTTokenUtils(baseContext).retrieveToken().isNullOrBlank()){
            NavControllerSingleton.getInstance().navigate(SignInDirections.actionNavSignInToNavCalendar())
        }

        binding.root.post {
            binding.root.findViewById<TextView>(R.id.txtV_sign_out)?.setOnClickListener {
                JWTTokenUtils(baseContext).clearToken()
                NavControllerSingleton.getInstance().navigate(CalendarFragmentDirections.actionNavCalendarToNavSignIn())
            }
        }

        // Background video.
        val backgroundVideo : VideoView = binding.appBarMain.content.videoView
        backgroundVideo.setVideoPath("android.resource://"+packageName+"/"+R.raw.backgroun_video)
        backgroundVideo.start()
        backgroundVideo.setOnPreparedListener {
            it.isLooping = true
        }

        navController.addOnDestinationChangedListener(validateSignInOrSignup)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()

        val backgroundVideo : VideoView = binding.appBarMain.content.videoView
        backgroundVideo.start()
    }
}
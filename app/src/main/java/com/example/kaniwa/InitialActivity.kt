package com.example.kaniwa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.activity_login.*

class InitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        screenSplash.setKeepOnScreenCondition{false}
        //Eliminar el action bar de la pantalla
        supportActionBar?.hide()

        listenerBotones()
    }
    private  fun listenerBotones(){
        botonRegistrar.setOnClickListener{

            val authIntent = Intent(this,AuthActivity::class.java).apply {}
            startActivity(authIntent)
        }
        botonIniciarSesion.setOnClickListener{
            val authIntent = Intent(this,LoginActivity::class.java).apply {}
            startActivity(authIntent)

        }
    }


}
package com.example.kaniwa

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerOpen:ImageView
    lateinit var navigationDrawer:NavigationView
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        //gUARDADO DE DATOS
        val prefs =
            getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        drawerOpen=findViewById(R.id.drawer_open)
        navigationDrawer=findViewById(R.id.navigation_drawer)
        drawerLayout=findViewById(R.id.drawer_layout)
        drawerOpen.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val navController:NavController= Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupWithNavController(navigationDrawer,navController)
    }
    private fun setup(email: String, provider: String) {
        title = "Inicio"
        /*
        binding.closeButton.setOnClickListener {
            //Borrado de datos de sesion
            val prefs =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
            */
    }
}
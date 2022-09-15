package com.example.kaniwa
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kaniwa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listenerBotones()
    }

    override fun onBackPressed() {}

    private  fun listenerBotones(){
        binding.botonRegistrar.setOnClickListener{
            val authIntent = Intent(this,AuthActivity::class.java).apply {}
            startActivity(authIntent)
        }
        binding.botonIniciarSesion.setOnClickListener{
            val authIntent = Intent(this,LoginActivity::class.java).apply {}
            startActivity(authIntent)
        }
    }
}
package com.example.kaniwa
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kaniwa.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setup
        val bundle = intent.extras
        val email= bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?:"",provider ?:"")
    }
    private fun setup(email:String, provider : String){
        title = "Inicio"
        binding.emailTextView.text=email
        binding.providerTextView.text=provider
        binding.closeButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}
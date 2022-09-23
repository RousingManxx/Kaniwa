package com.example.kaniwa
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kaniwa.R.string
import com.example.kaniwa.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen",bundle)

        //SetUp
        setup()
        sesion()
    }



    override fun onStart() {
        super.onStart()
        binding.authlayout.visibility= View.VISIBLE

    }
    private fun sesion(){
        val prefs = getSharedPreferences(getString(string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        if (email !=null && provider != null){
            binding.authlayout.visibility= View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

    }
    private  fun  setup(){
        title="Registro de Nuevo Usuario"
        binding.signButton.setOnClickListener{
            if (binding.emailTextField.toString().isNotEmpty() && binding.passwordTextField.toString().isNotEmpty()){
                println(binding.emailTextField.toString())
                println(binding.passwordTextField.toString())
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.emailTextField.editText?.text.toString(),binding.passwordTextField.editText?.text.toString()).addOnCompleteListener(){
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?:"",ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }

    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }


}
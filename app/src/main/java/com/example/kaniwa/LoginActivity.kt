package com.example.kaniwa
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.kaniwa.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private  val GOOGLE_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
        sesion()
    }

    override fun onBackPressed() {}
    override fun onStart() {
        super.onStart()
        //binding.loginlayout.visibility= View.VISIBLE
        this.setVisible(true)


    }
    private fun sesion(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        if (email !=null && provider != null){
            //binding.loginlayout.visibility= View.INVISIBLE
            this.setVisible(false)
            showHome(email, ProviderType.valueOf(provider))
        }

    }

    /*private fun setup(){
        title = "Inicio de Sesión"
        binding.loginButton.setOnClickListener {
            if (binding.emailEditText2.text.isNotEmpty() && binding.passwordEditText2.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEditText2.text.toString(),
                    binding.passwordEditText2.text.toString()
                ).addOnCompleteListener() {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }*/

    private fun setup(){
        binding.loginButton.setOnClickListener(){
            val email = binding.emailTextField.editText?.text.toString()
            val password = binding.passwordTextField.editText?.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener()
                {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
        binding.googleButton.setOnClickListener {
            //configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
        }

        binding.textLink.setOnClickListener(){
            val authIntent = Intent(this,AuthActivity::class.java).apply{}
            startActivity(authIntent)
        }

        binding.recuperar.setOnClickListener(){
            val forgotIntent = Intent(this, ForgotPassActivity::class.java).apply{}
            startActivity(forgotIntent)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val  task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if (it.isSuccessful){
                            showHome(account.email ?:"",ProviderType.GOOGLE)
                        }else{
                            showAlert()
                        }
                    }
                }
            }catch (e : ApiException){

            }
        }
    }
}
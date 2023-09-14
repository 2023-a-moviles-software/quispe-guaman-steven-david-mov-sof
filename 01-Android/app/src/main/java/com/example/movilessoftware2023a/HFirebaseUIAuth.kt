package com.example.movilessoftware2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {

    //CALLBAK INTENT DE LOGIN
    private var respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK){
            if (res.idpResponse != null){
                //Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse){
        val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        if(res.isNewUser == true){
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse){/*usuario.email; usuario.phoneNumber; usuario.user.name*/}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                //Arreglo de providers para logearse
            //EJ:Correo, Facebook, Twitter. etc
            AuthUI.IdpConfig.EmailBuilder().build()
            )
            //Intent de login
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            //Respuesta del intent
            respuestaLoginAuthUi.launch(signInIntent)
        }
        val btnLogOut = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogOut.setOnClickListener { seDeslogeo() }
        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null){
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
            val btnLogOut = findViewById<Button>(R.id.btn_logout_firebase)
            btnLogOut.visibility = View.INVISIBLE
            btnLogin.visibility = View.VISIBLE
            tvBienvenido.text = FirebaseAuth.getInstance().currentUser?.displayName
        }
    }

    fun seDeslogeo(){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogOut = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogOut.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
        tvBienvenido.text = "Bienvenido"
        FirebaseAuth.getInstance().signOut()
    }
}
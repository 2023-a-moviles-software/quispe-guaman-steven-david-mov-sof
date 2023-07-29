package com.example.movilessoftware2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

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
    }


}
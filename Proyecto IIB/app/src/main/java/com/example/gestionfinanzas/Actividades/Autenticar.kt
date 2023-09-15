package com.example.gestionfinanzas.Actividades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gestionfinanzas.Modelos.BaseDeDatosFirestore
import com.example.gestionfinanzas.Modelos.Persona
import com.example.gestionfinanzas.R

class Autenticar : AppCompatActivity() {
    private var usuarioAutenticado: Persona? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_autenticar)

            val emailEditText = findViewById<EditText>(R.id.txt_correo)
            val passwordEditText = findViewById<EditText>(R.id.txt_contrasenia)
            val loginButton = findViewById<Button>(R.id.btn_login)
            val registraseButton = findViewById<Button>(R.id.btn_ir_registro)

            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                BaseDeDatosFirestore.obtenerDatosUsuario { usuario ->
                    if (usuario != null) {
                        usuarioAutenticado = usuario
                        irActividad(MainBalance::class.java)
                    } else {
                        Log.e("Error", "No puede logear al usuario")
                    }
                }
            }

        registraseButton.setOnClickListener {
            irActividad(Registro::class.java)
        }

    }

    private fun irActividad(activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.putExtra("idPersona", usuarioAutenticado?.idPersona)
        startActivity(intent)
    }
}
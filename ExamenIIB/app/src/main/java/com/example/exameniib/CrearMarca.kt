package com.example.exameniib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.exameniib.models.Laptop
import com.example.exameniib.models.Marca
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearMarca : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_marca)

        val txtNombre = findViewById<EditText>(R.id.txt_nombre_marca)
        val txtFechaCreacion = findViewById<EditText>(R.id.txt_fecha_creacion)
        val sw_servicioTecnico = findViewById<Switch>(R.id.sw_servicioTecnico)
        val txtcontacto = findViewById<EditText>(R.id.txt_contacto)
        val btnaCrearMarca = findViewById<Button>(R.id.btn_crear_marca)

        btnaCrearMarca.setOnClickListener {

            val nombre = txtNombre.text.toString()
            val fechaCreacion = txtFechaCreacion.text.toString()
            val contacto = txtcontacto.text.toString()
            var servicioTecnico = "false"
            if (sw_servicioTecnico.isChecked){
                servicioTecnico = "true"
            }

            crearMarca(nombre, fechaCreacion, contacto, servicioTecnico)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun crearMarca(
        nombre: String,
        fechaCreacion: String,
        contacto: String,
        servicioTecnico: String
    ) {
        val nuevaMarca = Marca(
            idMarca = null,
            nombre = nombre,
            fechaCreacion = fechaCreacion,
            servicioTecnico = servicioTecnico,
            contacto = contacto
        )

        val db = Firebase.firestore

        db.collection("marca")
            .add(nuevaMarca)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }
}
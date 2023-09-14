package com.example.exameniib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActualizarMarca : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_marca)

        val marcaID = intent.getStringExtra("idAProducto")

        val txtNombre = findViewById<EditText>(R.id.txt_nombre_marca_actualizar)
        val txtFechaCreacion = findViewById<EditText>(R.id.txt_fecha_creacion_actualizar)
        val sw_servicioTecnico = findViewById<Switch>(R.id.sw_servicioTecnico_actualizar)
        val txtcontacto = findViewById<EditText>(R.id.txt_contacto_actualizar)
        val btnGuardar = findViewById<Button>(R.id.btn_actualizar_marca)

        val db = Firebase.firestore
        val almacenRef = marcaID?.let { db.collection("marca").document(it) }

        almacenRef?.get()
            ?.addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val marcaData = documentSnapshot.data
                    if (marcaData != null) {
                        val nombre = marcaData["nombre"] as? String
                        val fechaCreacion = marcaData["fechaCreacion"] as? String
                        val servicioTecnico = marcaData["servicioTecnico"] as? String
                        val contacto = marcaData["contacto"] as? String
                        if (nombre != null && fechaCreacion != null && servicioTecnico != null && contacto != null) {
                            txtNombre.setText(nombre)
                            txtFechaCreacion.setText(fechaCreacion)
                            if (servicioTecnico.toBoolean()){
                                sw_servicioTecnico.isChecked = true
                            }
                            txtcontacto.setText(contacto)
                        }
                    }
                }
            }
            ?.addOnFailureListener {  }



        btnGuardar.setOnClickListener {
            if (marcaID != null) {
                val db1 = Firebase.firestore
                val marcaRef1 = db1.collection("marca").document(marcaID)
                val nuevoNombre = txtNombre.text.toString()
                val nuevaFechaCreacion = txtFechaCreacion.text.toString()
                val nuevoContacto = txtcontacto.text.toString()
                var nuevoServicioTecnico = "false"
                if (sw_servicioTecnico.isChecked){
                    nuevoServicioTecnico = "true"
                }

                val nuevosDatos = hashMapOf(
                    "nombre" to nuevoNombre,
                    "fechaCreacion" to nuevaFechaCreacion,
                    "servicioTecnico" to nuevoServicioTecnico,
                    "contacto" to nuevoContacto
                )

                marcaRef1.update(nuevosDatos as Map<String, Any>)
                    .addOnSuccessListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { }
            }
        }
    }
}
package com.example.exameniib

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActualizarLaptop : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_laptop)


        val idLaptop = intent.getStringExtra("idLaptop")
        val idMarca = intent.getStringExtra("idMarca")

        val txtModelo = findViewById<EditText>(R.id.txt_modelo_actualizar)
        val txtPrecio = findViewById<EditText>(R.id.txt_precio_actualizar)
        val txtFechaLanzamiento = findViewById<EditText>(R.id.txt_fecha_lanzamiento_actualizar)
        val txtEnProduccion = findViewById<Switch>(R.id.sw_en_produccion_actualizar)
        val btnActualizar = findViewById<Button>(R.id.btn_actualizar_laptop)

        Log.i("RECIBE ID laptop?", "Esta es la id que recibe ${idLaptop}")

        val db = Firebase.firestore
        val laptopRef = idLaptop
            ?.let {
                db.collection("laptop")
                    .document(it)
            }

        laptopRef?.get()
            ?.addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val laptopData = documentSnapshot.data
                    if (laptopData != null) {
                        val modelo = laptopData["modelo"] as? String
                        val precio = laptopData["precio"] as? String
                        val fechaLanzamiento = laptopData["fechaLanzamiento"] as? String
                        val enProduccion = laptopData["enProduccion"] as? String

                        if (modelo != null && precio != null && fechaLanzamiento != null && enProduccion != null) {
                            txtModelo.setText(modelo)
                            txtPrecio.setText(precio)
                            txtFechaLanzamiento.setText(fechaLanzamiento)
                            if (enProduccion.toBoolean()){
                                txtEnProduccion.isChecked = true
                            }
                        }
                    }
                } else {
                    Log.e("Laptop", "El documento no existe.")
                }
            }
            ?.addOnFailureListener { e ->
                Log.e("Laptop", "Error al obtener el documento: $e")
            }

        btnActualizar.setOnClickListener {
            if (idLaptop != null) {
                val db1 = Firebase.firestore
                val laptopRef1 = db1.collection("laptop").document(idLaptop)
                val nuevoModelo = txtModelo.text.toString()
                val nuevoPrecio = txtPrecio.text.toString()
                val nuevaFechaLanzamiento = txtFechaLanzamiento.text.toString()
                var nuevoEnProduccion = "false"
                if (txtEnProduccion.isChecked){
                    nuevoEnProduccion = "true"
                }

                val nuevosDatos = hashMapOf(
                    "modelo" to nuevoModelo,
                    "precio" to nuevoPrecio,
                    "fechaLanzamiento" to nuevaFechaLanzamiento,
                    "enProduccion" to nuevoEnProduccion
                )

                laptopRef1.update(nuevosDatos as Map<String, Any>)
                    .addOnSuccessListener {
                        val intent = Intent(this, LaptopList::class.java)
                        intent.putExtra("idAProducto", idMarca)
                        startActivity(intent)
                    }
                    .addOnFailureListener { }
            }
        }
    }
}
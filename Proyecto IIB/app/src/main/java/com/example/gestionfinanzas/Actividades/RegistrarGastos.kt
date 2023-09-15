package com.example.gestionfinanzas.Actividades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.gestionfinanzas.Modelos.BaseDeDatosFirestore
import com.example.gestionfinanzas.Modelos.Cuenta
import com.example.gestionfinanzas.Modelos.Gasto
import com.example.gestionfinanzas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegistrarGastos : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_gastos)
        val btn_guardar_gasto = findViewById<Button>(R.id.btn_guardar_ingreso)
        val idCuenta = intent.getStringExtra("idCuenta")

        btn_guardar_gasto.setOnClickListener {
            guardarGasto(idCuenta)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun irActividad(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        val auth = FirebaseAuth.getInstance()
        // Verifica si hay un usuario autenticado
        val usuarioActual: FirebaseUser? = auth.currentUser
        if (usuarioActual != null){
            intent.putExtra("idPersona", usuarioActual.uid)
        }
        startActivity(intent)
    }

    fun guardarGasto(idCuenta: String?){
        val monto = findViewById<EditText>(R.id.txt_monto_gasto)
        val fecha = findViewById<EditText>(R.id.txt_fecha_gasto)
        val categoria = findViewById<EditText>(R.id.txt_categoria_gasto)
        val descripcion = findViewById<EditText>(R.id.txt_descripcion_gasto)

        if (
            monto.text.isNotEmpty()&&
            fecha.text.isNotEmpty() &&
            categoria.text.isNotEmpty() &&
            descripcion.text.isNotEmpty()
        ) {
            BaseDeDatosFirestore.crearGasto(
                Gasto(
                idCuenta?: "",
                monto.text.toString().toDouble(),
                fecha.text.toString(),
                categoria.text.toString(),
                descripcion.text.toString(),
            )
            )
            finish()
        }
        BaseDeDatosFirestore.actualizarTotalGastos(idCuenta?:"")
        BaseDeDatosFirestore.actualizarSaldoTotal(idCuenta?:"")
        irActividad(MainBalance::class.java)
    }
}
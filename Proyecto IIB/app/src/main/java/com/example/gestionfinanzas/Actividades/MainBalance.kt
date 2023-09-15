package com.example.gestionfinanzas.Actividades

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.gestionfinanzas.Modelos.BaseDeDatosFirestore
import com.example.gestionfinanzas.Modelos.Cuenta
import com.example.gestionfinanzas.R

class MainBalance : AppCompatActivity() {
    private var cuentaUsuario: Cuenta? = null

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_balance)

        // Recepcion de id del usuario
        val idUsuario = intent.getStringExtra("idPersona")

        if (idUsuario != null){
            val txtSaldoTotal = findViewById<TextView>(R.id.txt_total_balance)
            val txtTotalIngresos = findViewById<TextView>(R.id.txt_total_ingreso)
            val txtTotalGastos = findViewById<TextView>(R.id.txt_total_gastos)

            //Balance de la cuenta del usuario
            BaseDeDatosFirestore.obtenerCuentaPorIdPersona(idUsuario){ cuenta ->
                if (cuenta != null) {
                    cuentaUsuario = cuenta

                    val saldoTotalText = String.format("%.2f", cuenta.saldoTotal)
                    val totalIngresosText = String.format("%.2f", cuenta.totalIngresos)
                    val totalGastosText = String.format("%.2f", cuenta.totalGastos)

                    txtSaldoTotal.text = "$ $saldoTotalText"
                    txtTotalIngresos.text = "$ $totalIngresosText"
                    txtTotalGastos.text = "$ $totalGastosText"
                } else {
                        Log.i("Cuenta", "Error")
                }
            }

            // Main Activity
            val btn_agregarIngreso = findViewById<Button>(R.id.btn_nuevo_ingreso)
            val btn_agregarGasto = findViewById<Button>(R.id.btn_nuevo_gasto)
            val btn_detallesIngreso = findViewById<Button>(R.id.btn_detalles_ingresos)
            val btn_detallesGasto = findViewById<Button>(R.id.btn_detalles_gastos)

            btn_agregarIngreso.setOnClickListener {
                irActividad(RegistrarIngresos::class.java)
            }
            btn_agregarGasto.setOnClickListener {
                irActividad(RegistrarGastos::class.java)
                BaseDeDatosFirestore.actualizarTotalIngresos(cuentaUsuario?.idCuenta?:"")
                BaseDeDatosFirestore.actualizarSaldoTotal(cuentaUsuario?.idCuenta?:"")
            }
            btn_detallesIngreso.setOnClickListener {
                irActividad(DetalleIngresos::class.java)
            }
            btn_detallesGasto.setOnClickListener {
                irActividad(DetalleGastos::class.java)
            }


        }else{
            Log.e("Error", "Usuario no encontrado")
        }
    }


    override fun onStart() {
        super.onStart()
    }

    private fun irActividad(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        intent.putExtra("idCuenta", cuentaUsuario?.idCuenta)
        startActivity(intent)
    }
}
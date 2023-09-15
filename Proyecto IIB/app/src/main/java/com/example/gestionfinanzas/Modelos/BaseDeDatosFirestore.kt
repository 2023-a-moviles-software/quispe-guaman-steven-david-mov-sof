package com.example.gestionfinanzas.Modelos

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class BaseDeDatosFirestore {
    private val db = FirebaseFirestore.getInstance()

    companion object {

        @SuppressLint("StaticFieldLeak")
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        // Métodos para Persona
        fun obtenerDatosUsuario(callback: (Persona?) -> Unit) {
            val auth = FirebaseAuth.getInstance()
            val usuarioActual = auth.currentUser

            if (usuarioActual != null) {
                // El usuario está autenticado, puedes acceder a sus datos
                val uid = usuarioActual.uid
                val nombre = usuarioActual.displayName
                val email = usuarioActual.email

                // Crea un objeto Usuario con los datos
                val usuario = Persona(uid, nombre, email, "")

                // Llama al callback con el objeto Usuario
                callback(usuario)
            } else {
                // El usuario no está autenticado, llama al callback con null
                callback(null)
            }
        }

        fun obtenerCuentaPorIdPersona(idPersona: String, callback: (Cuenta?) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val cuentasRef = db.collection("Cuenta") // Reemplaza "cuentas" con el nombre de tu colección en Firestore
                .whereEqualTo("idPersona", idPersona)

            cuentasRef.get()
                .addOnSuccessListener { result ->
                    for(document in result) {
                        val saldoTotal = document.getDouble("saldoTotal")
                        val idPersona = document.getString("idPersona")?: ""
                        val totalGastos = document.getDouble("totalGastos")
                        val totalIngresos = document.getDouble("totalIngresos")
                        val fechaCreacion = document.getString("fechaCreacion")?: ""

                        if (saldoTotal != null && totalGastos != null && totalIngresos != null) {
                            val cuenta = Cuenta(document.id, idPersona, fechaCreacion, saldoTotal, totalIngresos, totalGastos)
                            callback(cuenta)
                        } else {
                            Log.i("Cuenta", "Datos incompletos")
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.i("Cuenta", "Error BDD")
                }
        }

        fun actualizarTotalIngresos(idCuenta: String) {
            val db = FirebaseFirestore.getInstance()
            val cuentasRef = db.collection("Ingreso")
                .whereEqualTo("idCuenta", idCuenta)

            cuentasRef.get()
                .addOnSuccessListener { result ->
                    var totalIngresos = 0.0 // Inicializa el total en 0

                    for (document in result) {
                        val monto = document.getDouble("monto")

                        if (monto != null) {
                            // Suma el monto al total
                            totalIngresos += monto
                        } else {
                            Log.i("Ingreso", "Monto nulo en un ingreso")
                        }
                    }

                    // Actualiza el valor totalIngresos en la base de datos
                    val cuentasDocRef = db.collection("Cuenta").document(idCuenta)
                    cuentasDocRef
                        .update("totalIngresos", totalIngresos)
                        .addOnSuccessListener {
                            Log.i("Ingreso", "Total de ingresos actualizado con éxito")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Ingreso", "Error al actualizar el total de ingresos", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("Ingreso", "Error al consultar ingresos", e)
                }
        }

        fun actualizarTotalGastos(idCuenta: String) {
            val db = FirebaseFirestore.getInstance()
            val cuentasRef = db.collection("Gasto")
                .whereEqualTo("idCuenta", idCuenta)

            cuentasRef.get()
                .addOnSuccessListener { result ->
                    var totalGastos = 0.0 // Inicializa el total en 0

                    for (document in result) {
                        val monto = document.getDouble("monto")

                        if (monto != null) {
                            // Suma el monto al total
                            totalGastos += monto
                        } else {
                            Log.i("Gasto", "Monto nulo en un gasto")
                        }
                    }

                    // Actualiza el valor totalIngresos en la base de datos
                    val cuentasDocRef = db.collection("Cuenta").document(idCuenta)
                    cuentasDocRef
                        .update("totalGastos", totalGastos)
                        .addOnSuccessListener {
                            Log.i("Gasto", "Total de gastos actualizado con éxito")
                        }
                        .addOnFailureListener { e ->
                            Log.e("Gasto", "Error al actualizar el total de gastos", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e("Gasto", "Error al consultar gastos", e)
                }
        }

        fun actualizarSaldoTotal(idCuenta: String) {
            val db = FirebaseFirestore.getInstance()
            val cuentaDocRef = db.collection("Cuenta").document(idCuenta)

            // Realiza la consulta para obtener los datos de la cuenta
            cuentaDocRef.get()
                .addOnSuccessListener { cuentaDocument ->
                    val totalGastos = cuentaDocument.getDouble("totalGastos") ?: 0.0
                    val totalIngresos = cuentaDocument.getDouble("totalIngresos") ?: 0.0

                    val nuevoSaldoTotal = totalIngresos - totalGastos
                    cuentaDocRef.update("saldoTotal", nuevoSaldoTotal)
                }
                .addOnFailureListener { e ->
                    Log.e("Actualización de saldo", "Error al obtener datos de la cuenta", e)
                }
        }

        fun crearIngreso(gasto: Gasto) {
            db.collection("Ingreso")
                .document(gasto.descripcion)
                .set(gasto)
                .addOnSuccessListener { documentReference ->
                    println("Se agrego un ingreso")
                }
                .addOnFailureListener { e ->
                    println("Error al agregar el gasto: $e")
                }
        }

        fun crearGasto(gasto: Gasto) {
            db.collection("Gasto")
                .document(gasto.descripcion)
                .set(gasto)
                .addOnSuccessListener { documentReference ->
                    println("Se agrego un gasto")
                }
                .addOnFailureListener { e ->
                    println("Error al agregar el gasto: $e")
                }
        }


        fun registrarUsuarioConCuentaEnFirebase(
            email: String,
            contrasenia: String,
            nombre: String,
            callback: (Boolean, FirebaseUser?) -> Unit
        ) {
            val auth = FirebaseAuth.getInstance()
            val db = FirebaseFirestore.getInstance()

            // Crear el usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(email, contrasenia)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso, se devuelve el usuario creado
                        val usuario = task.result?.user

                        // Crear un documento de usuario en Firestore
                        val usuarioData = hashMapOf(
                            "nombre" to nombre,
                            "correo" to email,
                            "contrasenia" to contrasenia
                        )

                        usuario?.uid?.let { uid ->
                            val usuariosRef = db.collection("Persona")
                            usuariosRef.document(uid)
                                .set(usuarioData)
                                .addOnSuccessListener {
                                    // Documento de usuario creado en Firestore
                                    // Ahora, crea una cuenta para el usuario
                                    val cuentaData = hashMapOf(
                                        "idPersona" to uid,
                                        "saldoTotal" to 0.0, // Puedes establecer un saldo inicial si es necesario
                                        "totalGastos" to 0.0,
                                        "totalIngresos" to 0.0,
                                        "fechaCreacion" to ""
                                        // Agrega otros campos de cuenta según tus necesidades
                                    )

                                    val cuentasRef = db.collection("Cuenta")
                                    cuentasRef.add(cuentaData)
                                        .addOnSuccessListener {
                                            // Cuenta creada exitosamente
                                            callback(true, usuario)
                                        }
                                        .addOnFailureListener { e ->
                                            // Error al crear la cuenta en Firestore
                                            callback(false, null)
                                        }
                                }
                                .addOnFailureListener { e ->
                                    // Error al crear el documento de usuario en Firestore
                                    callback(false, null)
                                }
                        } ?: run {
                            // El usuario es nulo
                            callback(false, null)
                        }
                    } else {
                        // El registro falló, se devuelve null
                        callback(false, null)
                    }
                }
        }



    }
}
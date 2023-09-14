package com.example.exameniib;

import android.annotation.SuppressLint
import com.example.exameniib.models.Laptop
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestorBDD {
    companion object {

        @SuppressLint("StaticFieldLeak")
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        private val marcasCollection = db.collection("marcas")

       suspend fun obtenerTodosLasLaptos(idMarca: String): List<Laptop> {
            val laptopLsit = mutableListOf<Laptop>()
            return try {
                val querySnapshot = marcasCollection.document(idMarca)
                    .collection("laptop")
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot) {
                        val laptopId = document.getString("idLaptop")
                        val modelo = document.getString("modelo")
                        val idMarca = document.getString("idMarca")
                        val precio = document.getString("precio")
                        val fechaLanzamiento = document.getString("fechaLanzamiento")
                        val enProduccion = document.getString("enProduccion")

                        val nuevaLaptop =
                            Laptop(laptopId, modelo, idMarca, precio, fechaLanzamiento, enProduccion)
                        laptopLsit.add(nuevaLaptop)
                    }
                }
                return laptopLsit

            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    }

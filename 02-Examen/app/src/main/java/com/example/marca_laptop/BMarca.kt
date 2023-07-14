package com.example.marca_laptop

import java.time.LocalDate

class BMarca (
    var idMarca: Int,
    var nombre: String,
    var fechaCreacion: LocalDate,
    var servicioTecnico: Boolean,
    var contacto: String
        ){

    override fun toString(): String {
        return "${idMarca} - ${nombre} - ${fechaCreacion} - ${servicioTecnico} - ${contacto}"
    }

}
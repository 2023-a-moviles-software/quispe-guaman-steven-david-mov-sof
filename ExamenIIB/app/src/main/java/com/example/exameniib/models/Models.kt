package com.example.exameniib.models

data class Marca(
    var idMarca: String?,
    var nombre: String?,
    var fechaCreacion: String?,
    var servicioTecnico: String?,
    var contacto: String?,
)

data class Laptop(
    var idLaptop: String?,
    var modelo: String?,
    var idMarca: String?,
    var precio: String?,
    var fechaLanzamiento: String?,
    var enProduccion: String?,
)
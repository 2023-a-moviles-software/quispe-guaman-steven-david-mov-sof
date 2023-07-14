package com.example.marca_laptop

import java.time.LocalDate

class BLaptop (
    var idLaptop: Int,
    var modelo: String,
    var idMarca: Int,
    var precio: Double,
    var fechaLanzamiento: LocalDate,
    var enProduccion: Boolean
        ){

    override fun toString(): String {
        return "${idLaptop} - ${modelo}  - $ ${precio} - ${fechaLanzamiento} " +
                "- ${enProduccion}"
    }
}
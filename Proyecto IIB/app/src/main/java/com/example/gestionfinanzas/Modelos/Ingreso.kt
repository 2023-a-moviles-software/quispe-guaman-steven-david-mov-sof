package com.example.gestionfinanzas.Modelos

class Ingreso {
    var idCuenta: String
    var monto:Double
    var fecha: String
    var categoria: String
    var descripcion: String

    constructor(
        idCuenta: String,
        monto:Double,
        fecha: String,
        categoria: String,
        descripcion: String

        ) {
        this.idCuenta = idCuenta
        this.monto = monto
        this.fecha = fecha
        this.categoria = categoria
        this.descripcion = descripcion
    }
}
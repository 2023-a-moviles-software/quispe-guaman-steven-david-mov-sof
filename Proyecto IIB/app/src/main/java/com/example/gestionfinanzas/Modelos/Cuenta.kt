package com.example.gestionfinanzas.Modelos

class Cuenta {
    var idCuenta: String
    var idPersona: String
    var fechaCreacion: String
    var saldoTotal:Double
    var totalIngresos:Double
    var totalGastos:Double

    constructor(
        idCuenta: String,
        idPersona: String,
        fechaCreacion: String,
        saldoTotal:Double,
        totalIngresos:Double,
        totalGastos:Double,

    ) {
        this.idCuenta = idCuenta
        this.idPersona = idPersona
        this.fechaCreacion = fechaCreacion
        this.saldoTotal = saldoTotal
        this.totalIngresos = totalIngresos
        this.totalGastos = totalGastos
    }
}
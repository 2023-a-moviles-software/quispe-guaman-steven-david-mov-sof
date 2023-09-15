package com.example.gestionfinanzas.Modelos

class Persona {
    var idPersona: String?
    var nombre: String?
    var correo: String?
    var contrasenia: String?

    constructor(
        idPersona: String?,
        nombre: String?,
        correo: String?,
        contrasenia: String?

    ) {
        this.idPersona = idPersona
        this.nombre = nombre
        this.correo = correo
        this.contrasenia = contrasenia
    }
}
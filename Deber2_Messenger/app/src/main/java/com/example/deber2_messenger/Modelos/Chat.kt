package com.example.deber2_messenger.Modelos

class Chat (
    var nombreUsuario: String,
    var mensaje: String,
    var fecha: String,
    var nombreImagenPerfil: String,
        ){

    companion object {
        val arregloChats = arrayListOf<Chat>()
        init {
            arregloChats
                .add(
                    Chat("Maria Perez", "¿Cómo estás?  ", "-  22 jul", "chat1")
                )
            arregloChats
                .add(
                    Chat("Pedro Paez", "Listo, sin problema  ", "-  12 jul", "chat2")
                )
            arregloChats
                .add(
                    Chat("Hernán Reyes", "Hasta luego  ", "-  10 abr", "chat3")
                )
            arregloChats
                .add(
                    Chat("Marco Rojas", "Te envío la proforma  ", "-  15 mar", "chat4")
                )
            arregloChats
                .add(
                    Chat("Laura Díaz", "Hola :D  ", "-  14 mar", "chat5")
                )
            arregloChats
                .add(
                    Chat("Jhon Narvaez", "Aquí tienes la tarea  ", "-  9 mar", "chat6")
                )
            arregloChats
                .add(
                    Chat("María Paz", "Buenas tardes, adjunto document ....  ", "-  8 mar", "chat7")
                )
            arregloChats
                .add(
                    Chat("Joan Díaz", "Invitación reunión virtual Zoom ....  ", "-  8 mar", "chat8")
                )
            arregloChats
                .add(
                    Chat("Javier Nieto", "Emisión de certificados de Auto ....  ", "-  1 mar", "chat9")
                )
            arregloChats
                .add(
                    Chat("Selene Averos", "Crédito de Consumo Universal  ", "-  1 mar", "chat10")
                )
        }
    }

}
import org.gradle.internal.impldep.org.joda.time.LocalDate

class Marca {
    //Atributes
    val idMarca: Int
    var nombre: Stringvar
    var fechaCreacion: LocalDate
    var servicioTecnico: Boolean
    var contacto: String
    var laptops: ArrayList<Laptop>

    private var numeroMarcasRegistradas = 0

    //CREATE
    constructor(
        id:Int,
        nombre:String,
        fechaCreacion: LocalDate,
        servicioTecnico: Boolean,
        contacto: String
    ){
        this.idMarca = id
        this.nombre = nombre
        this.fechaCreacion = fechaCreacion
        this.servicioTecnico = servicioTecnico
        this.contacto = contacto
        this.laptops = ArrayList<Laptop>()
    }

    fun almacenarDatosMarca(archivo: File){
        try{
            var textoPorGuardar = "${this.idMarca},${this.nombre},${this.fechaCreacion},${this.contacto}," +
                    "${this.servicioTecnico}" + "\n"
                        archivo.appendText(textoPorGuardar)
        }catch (e: Exeption){
            println("Error durante el almacenamiento de datos")
        }
    }

    //READ
    fun listarMarcasRegistradas(archivo: File){
        if(archivo.exists()){
            archivo.readLines().forEach {line ->
                val marca = line.split(",")
                println("Marca id: " + marca[0]
                + "; " + "Nombre: " + marca[1]
                + "; " + "Fecha de creacion: " + marca[2]
                + "; " + "Contacto: " + marca[3]
                + "; " + "Ofrece servicio Técnico: " + marca[4])
            }
        }
    }

    //UPDATE
    fun actuaizarDatosMarca(
        nombre:String,
        fechaCreacion: LocalDate,
        servicioTecnico: Boolean,
        contacto: String
    ){
        this.nombre = nombre
        this.fechaCreacion = fechaCreacion
        this.servicioTecnico = servicioTecnico
        this.contacto = contacto
    }



}
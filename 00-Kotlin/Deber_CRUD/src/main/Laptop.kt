class Laptop {
    //Atributes
    val idLaptop: Int
    var modelo: String
    var marca: String
    var precio: Double
    var fechaLanzamiento: LocalDate
    var enProduccion: Boolean

    private var numeroLaptopsRegistradas = 0

    //CREATE
    constructor(
        id:Int,
        modelo:String,
        marca:String,
        precio: Double,
        fechaLanzamiento: LocalDate,
        enProduccion: Boolean
    ){
        this.idLaptop = id
        this.marca = marca
        this.modelo = modelo
        this.precio = precio
        this.fechaLanzamiento = fechaLanzamiento
        this.enProduccion = enProduccion
    }

    fun almacenarDatosLaptop(archivo: File){
        try{
            var textoPorGuardar = "${this.idLaptop},${this.marca},${this.modelo},${this.fechaLanzamiento}," +
                    "${this.enProduccion}" + "\n"
            archivo.appendText(textoPorGuardar)
        }catch (e: Exeption){
            println("Error durante el almacenamiento de datos")
        }
    }

    //READ
    fun listarLaptopsRegistradas(archivo: File){
        if(archivo.exists()){
            archivo.readLines().forEach {line ->
                val laptop = line.split(",")
                println("Laptop id: " + laptop[0]
                        + "; " + "Marca: " + laptop[1]
                        + "; " + "Modelo: " + laptop[2]
                        + "; " + "Precio: $: " + laptop[3]
                        + "; " + "Fecha de lanzamiento: " + laptop[4]
                        + "; " + "En produccion: " + laptop[5])
            }
        }
    }

    //UPDATE
    fun actuaizarDatosLaptop(
        precio: Double,
        enProduccion: Boolean
    ){
        this.precio = precio
        this.enProduccion = enProduccion
    }

    
}
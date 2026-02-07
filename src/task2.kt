fun separation_by_space_filter_alf(){
    println("Введите строку: ")
    val input: String? = readlnOrNull()
    val list = input?.split(" ")?.sorted()
    list?.forEach {println(it)}
}

fun main(){
    separation_by_space_filter_alf()
}

fun separation_by_space_filter_alf(){
    val input: String? = readlnOrNull()
    val list = input?.split(" ")?.sorted()
    list?.forEach {println(it)}
}

fun main(){
    separation_by_space_filter_alf()
}

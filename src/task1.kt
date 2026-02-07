fun separation_by_space(){
    val input: String? = readlnOrNull()
    val list = input?.split(" ")
    list?.forEach {println(it)}
}

fun main(){
    separation_by_space()
}

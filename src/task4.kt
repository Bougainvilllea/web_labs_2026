fun separation_by_space_filter_alf_counts(){
    val input: String? = readlnOrNull()
    val list = input?.split(" ")?.sorted()?.groupingBy { it }?.eachCount()
    list?.forEach {(key, value) -> println("$key $value") }
}

fun main(){
    separation_by_space_filter_alf_counts()
}
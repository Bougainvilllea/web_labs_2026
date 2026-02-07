fun separation_by_space_filter_alf_distinct(){
    val input: String? = readlnOrNull()
    val list = input?.split(" ")?.sorted()?.distinct()
    list?.forEach {println(it)}
}

fun main(){
    separation_by_space_filter_alf_distinct()
}
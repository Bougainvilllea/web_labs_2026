fun separation_by_space_filter_num(){
    val input: String? = readlnOrNull()
    val list = input?.split(" ")
        ?.sorted()?.groupingBy { it }
        ?.eachCount()
        ?.toList()
        ?.sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })

    list?.forEach {(key, value) -> println("$key $value") }
}

fun main(){
    separation_by_space_filter_num()
}
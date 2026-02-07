fun separation_by_space_filter_num(input: Array<String>?){
        input?.sorted()
            ?.groupingBy { it }
            ?.eachCount()
            ?.toList()
            ?.sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })
            ?.forEach {(key, value) -> println("$key $value") }
}

fun main(args: Array<String>){
    separation_by_space_filter_num(args)
}
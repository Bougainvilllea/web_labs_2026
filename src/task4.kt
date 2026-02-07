fun separation_by_space_filter_alf_counts(input: Array<String>?){
    input?.sorted()
        ?.groupingBy { it }
        ?.eachCount()
        ?.forEach {(key, value) -> println("$key $value") }
}

fun main(args: Array<String>){
    separation_by_space_filter_alf_counts(args)
}
fun separation_by_space_filter_alf_distinct(input: Array<String>?)){
    input?.sorted()?.distinct()?.forEach {println(it)}
}

fun main(args: Array<String>)){
    separation_by_space_filter_alf_distinct(args)
}
import java.util.Scanner

fun separation_by_space_filter_stdin(input: String) : List<Pair<String, Int>> {

    val list = input.split(" ")
        .sorted().groupingBy { it }
        .eachCount()
        .toList()
        .sortedWith(compareByDescending<Pair<String, Int>> { it.second }.thenBy { it.first })
    return list

}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val input = args.joinToString(" ")
        separation_by_space_filter_stdin(input).forEach {(key, value) -> println("$key $value") }
    }
    else {
        val scanner = Scanner(System.`in`)
        separation_by_space_filter_stdin(scanner.nextLine()).forEach {(key, value) -> println("$key $value")}
        scanner.close()
    }
}
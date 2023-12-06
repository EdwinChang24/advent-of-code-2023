package day05

import readInput

fun main() {
    part1()
    part2()
}

private fun List<String>.subLists() = listOf(
    subList(3, 43),
    subList(45, 70),
    subList(72, 114),
    subList(116, 163),
    subList(165, 181),
    subList(183, 199),
    subList(201, 213)
)

fun part1() {
    val input = readInput("05", "in")
    println(input.subLists().map { it.map { line -> line.split(' ').map { s -> s.toLong() } } }.let { maps ->
        input.first().removePrefix("seeds: ").split(' ').map { it.toLong() }.minOf { seed ->
            maps.fold(seed) { acc, map ->
                map.firstOrNull { entry -> acc in entry[1]..<(entry[1] + entry[2]) }
                    ?.let { entry -> entry[0] + acc - entry[1] } ?: acc
            }
        }
    })
}

fun part2() {
    val input = readInput("05", "in")
    println(input.subLists().reversed().map { it.map { line -> line.split(' ').map { s -> s.toLong() } } }.let { maps ->
        input.first().removePrefix("seeds: ").split(' ').map { it.toLong() }.chunked(2) { it[0]..<(it[0] + it[1]) }
            .let { ranges ->
                (0..Long.MAX_VALUE).first { location ->
                    maps.fold(location) { acc, map ->
                        map.firstOrNull { entry -> acc in entry[0]..<(entry[0] + entry[2]) }
                            ?.let { entry -> entry[1] + acc - entry[0] } ?: acc
                    }.let { seed -> ranges.any { seed in it } }
                }
            }
    })
}

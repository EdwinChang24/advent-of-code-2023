package day14

import readInput
import kotlin.system.measureTimeMillis

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("14", "in")
    println(List(input.first().length) { a -> List(input.size) { b -> input[b][a] } }.sumOf { column ->
        column.indices.filter { column[it] == 'O' }
            .map { index -> column.slice(0..<index).indexOfLast { it == '#' } + 1 }.groupBy { it }
            .mapValues { (_, list) -> list.size }
            .map { (index, count) -> (column.size - index - count + 1)..(column.size - index) }.sumOf { it.sum() }
    })
}

fun part2() {
    val m = measureTimeMillis {
        val input = readInput("14", "in")
        fun List<Char>.tilted() =
            indices.filter { this[it] == 'O' }.map { index -> slice(0..<index).indexOfLast { it == '#' } }
                .groupBy { it }
                .mapValues { (_, list) -> list.size }.map { (index, count) -> (index + 1)..(index + count) }
                .let { ranges ->
                    List(size) { index -> if (this[index] == '#') '#' else if (ranges.any { index in it }) 'O' else '.' }
                }

        fun List<List<Char>>.transposed() = List(first().size) { a -> List(size) { b -> this[b][a] } }
        fun List<List<Char>>.spinCycle() =
            map { it.toList() }.transposed().map { it.tilted() }.transposed().map { it.tilted() }.reversed()
                .transposed()
                .map { it.tilted() }.transposed().map { it.reversed().tilted().reversed() }.reversed()
        println((1..1_000_000_000).first { i ->
            (1..i).runningFold(input.map { it.toList() }) { acc, _ -> acc.spinCycle() }.map { it.toString() }
                .let { it.toSet().size != it.size }
        }.let { i ->
            (1..i).runningFold(input.map { it.toList() }) { acc, _ -> acc.spinCycle() }.map { it.toString() }
                .let { list ->
                    (1_000_000_000 - list.indexOfFirst { it == list.last() }) % (list.size - list.indexOfFirst { it == list.last() } - 1) + list.indexOfFirst { it == list.last() }
                }.let { j ->
                (1..j).fold(input.map { it.toList() }) { acc, _ -> acc.spinCycle() }.transposed()
                    .sumOf { row -> row.mapIndexed { index, c -> if (c == 'O') row.size - index else 0 }.sum() }
            }
        })
    }
    println(m)
}

package day15

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("15", "in")
    println(input.first().split(',').sumOf { it.fold(0) { acc, c -> (acc + c.code) * 17 % 256 }.toInt() })
}

fun part2() {
    val input = readInput("15", "in")
    fun String.hash() = fold(0) { acc, c -> (acc + c.code) * 17 % 256 }
    println(input.first().split(',').let { steps ->
        steps.fold(List(256) { emptyList<Pair<String, Int>>() }) { boxes, step ->
            if ('-' in step) boxes.mapIndexed { index, box ->
                if (index == step.removeSuffix("-").hash()) box.filterNot { it.first == step.removeSuffix("-") }
                else box
            } else boxes.mapIndexed { index, box ->
                if (index == step.split('=').first().hash()) if (box.any {
                        it.first == step.split('=').first()
                    }) box.map { lens ->
                    if (lens.first == step.split('=').first()) lens.first to step.split('=').last().toInt() else lens
                } else List(box.size + 1) {
                    box.getOrElse(it) { step.split('=').first() to step.split('=').last().toInt() }
                } else box
            }
        }.sumOf { lenses ->
            lenses.mapIndexed { index, lens -> (1 + lens.first.hash()) * (index + 1) * lens.second }.sum()
        }
    })
}

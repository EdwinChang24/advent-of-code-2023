package day07

import readInput

fun main() {
    part1()
    part2()
}

fun part1() {
    val input = readInput("07", "in")
    (mapOf(
        'A' to 12, 'K' to 11, 'Q' to 10, 'J' to 9, 'T' to 8
    ) + (2..9).map { it.digitToChar() to it - 2 }).let { labels ->
        println(input.map { line -> line.split(' ')[0] to line.split(' ')[1].toInt() }.sortedBy { (hand) ->
            labels[hand[4]]!! + (labels[hand[3]]!! * 13) + (labels[hand[2]]!! * 169) + (labels[hand[1]]!! * 2197) + (labels[hand[0]]!! * 28561) + when {
                hand.toSet().size == 1 -> 371293 * 6
                hand.any { hand.count { c -> c == it } == 4 } -> 371293 * 5
                hand.toSet().size == 2 -> 371293 * 4
                hand.any { hand.count { c -> c == it } == 3 } -> 371293 * 3
                hand.toSet().count { hand.count { c -> c == it } == 2 } == 2 -> 371293 * 2
                hand.any { hand.count { c -> c == it } == 2 } -> 371293
                else -> 0
            }
        }.mapIndexed { index, (_, bid) -> bid * (index + 1) }.sum())
    }
}

fun part2() {
    val input = readInput("07", "in")
    (mapOf(
        'A' to 12, 'K' to 11, 'Q' to 10, 'T' to 9, 'J' to 0
    ) + (2..9).map { it.digitToChar() to it - 1 }).let { labels ->
        println(input.map { line -> line.split(' ')[0] to line.split(' ')[1].toInt() }.sortedBy { (hand) ->
            labels[hand[4]]!! + (labels[hand[3]]!! * 13) + (labels[hand[2]]!! * 169) + (labels[hand[1]]!! * 2197) + (labels[hand[0]]!! * 28561) + (if (hand[0] == 'J') labels.keys else listOf(
                hand[0]
            )).maxOf { first ->
                (if (hand[1] == 'J') labels.keys else listOf(hand[1])).maxOf { second ->
                    (if (hand[2] == 'J') labels.keys else listOf(hand[2])).maxOf { third ->
                        (if (hand[3] == 'J') labels.keys else listOf(hand[3])).maxOf { fourth ->
                            (if (hand[4] == 'J') labels.keys else listOf(hand[4])).maxOf { fifth ->
                                "$first$second$third$fourth$fifth".let { newHand ->
                                    when {
                                        newHand.toSet().size == 1 -> 371293 * 6
                                        newHand.any { newHand.count { c -> c == it } == 4 } -> 371293 * 5
                                        newHand.toSet().size == 2 -> 371293 * 4
                                        newHand.any { newHand.count { c -> c == it } == 3 } -> 371293 * 3
                                        newHand.toSet()
                                            .count { newHand.count { c -> c == it } == 2 } == 2 -> 371293 * 2

                                        newHand.any { newHand.count { c -> c == it } == 2 } -> 371293
                                        else -> 0
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.mapIndexed { index, (_, bid) -> bid * (index + 1) }.sum()
        )
    }
}

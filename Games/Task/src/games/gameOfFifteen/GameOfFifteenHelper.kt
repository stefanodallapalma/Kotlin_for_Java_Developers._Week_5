package games.gameOfFifteen

import java.util.*

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val sortedPairs: List<Pair<Int, Int>> = permutation.sorted().cartesianProduct(permutation.sorted())
    val invertedPairs: Int = sortedPairs.count { permutation.indexOf(it.first) > permutation.indexOf(it.second) }

    return invertedPairs % 2 == 0
}

fun List<Int>.cartesianProduct(other : List<Int>) : List<Pair<Int, Int>> = flatMap {
    List(other.size){ i -> Pair(it, other[i]) }.filter { it.first < it.second }
}

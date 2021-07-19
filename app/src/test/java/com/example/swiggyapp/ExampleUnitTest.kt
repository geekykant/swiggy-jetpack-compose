package com.example.swiggyapp

//import androidx.compose.ui.Modifier
import org.junit.Test
import kotlin.math.min

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun check() {
        /* Spotlight Restaurants are in 6 columns x 2 rows
           We need to check and see they are in the limits for the sublist windowing.
        */

        val spotlightRestaurants = List(1) { it + 1 }

        val spotlightRestaurantsSublist =
            spotlightRestaurants.subList(0, min(12, nearestEven(spotlightRestaurants.size)))
        val windowSize = spotlightRestaurantsSublist.size / 2

        if(spotlightRestaurantsSublist.isNotEmpty()){
            println("Window size: $windowSize")
            println("before: ${spotlightRestaurants.size}")
            println("after: ${spotlightRestaurantsSublist.size}")

            spotlightRestaurantsSublist.windowed(windowSize, windowSize, true) { sublist ->
                println(sublist.size)
                sublist.forEach { item ->
                    println("## $item")
                }
            }
        }
    }

    private fun nearestEven(size: Int): Int {
        return if (size % 2 == 0) {
            size
        } else {
            size.dec()
        }
    }
}
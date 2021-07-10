package com.example.swiggyapp

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun check(){
        fun prepareContent(): HashMap<String, String> {
            val tagTaglineMap = HashMap<String, String>()
            tagTaglineMap["Restaurant"] = "Enjoy your favourite treats"
            tagTaglineMap["Genie"] = "Anything you need, delivered"
            tagTaglineMap["Meat"] = "Fresh meat & seafood"
            tagTaglineMap["Book Shops"] = "Delivery from Book Shops"
            tagTaglineMap["Care Corner"] = "Find essentials & help loved ones"
            return tagTaglineMap
        }

        val content = prepareContent()
        for((key, value) in content){
            println("First: $key Second: $value")
        }
    }
}
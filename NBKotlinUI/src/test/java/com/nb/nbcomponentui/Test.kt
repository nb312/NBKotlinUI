package com.nb.nbcomponentui

import org.junit.Test

/**
 * Created by NieBin on 2018-07-19
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
class TestD {
    @Test
    fun test() {
        var list = mutableListOf("B", "BB", "A", "a", "C", "B", "D", "Ac")
        list.sortBy {
            it[0]
        }
        println(list)
        println("this is test for you.")
    }
}
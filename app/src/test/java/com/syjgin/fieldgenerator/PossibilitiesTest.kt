package com.syjgin.fieldgenerator

import com.syjgin.fieldgenerator.generator.Generator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PossibilitiesTest {
    @Test
    fun createPossibilitiesArrayTest() {
        val map = mutableListOf<Pair<Int, String>>()
        map.add(Pair(40, "a"))
        map.add(Pair(60, "b"))
        val possibilities = Generator.createPossibilitiesArray(map)
        assertEquals(100, possibilities.size)
        assertEquals("a", possibilities[0])
        assertEquals("a", possibilities[39])
        assertEquals("b", possibilities[61])
        assertEquals("b", possibilities[99])
    }
}

package common.example.search

import org.junit.Test
import kotlin.test.assertEquals
import test.grep
import test.suma
import kotlin.test.assertContains

class GrepTest {
    companion object {
        val sampleData = listOf(
            "123 abc",
            "abc 123",
            "123 ABC",
            "ABC 123"
        )
    }

    @Test
    fun shouldFindMatches() {
        val results = mutableListOf<String>()
        grep(sampleData, "[a-z]+") {
            results.add(it)
        }

        assertEquals(2, results.size)
        for (result in results) {
            assertContains(result, "abc")
        }
    }


}
class SumaTest {
    /**
     * Successfull test
     */
    @Test
    fun sumaTest(){
        assertEquals(9, suma(4,5))
    }

    /**
     * Unsuccessful test
     */
    @Test
    fun sumaTest2(){
        assertEquals(8, suma(4,5))
    }

}
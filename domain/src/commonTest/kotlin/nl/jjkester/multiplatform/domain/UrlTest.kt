package nl.jjkester.multiplatform.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class UrlTest {

    @Test
    fun testUrlConstructor() {
        val url = Url("https://kotlinlang.org")
        assertEquals("https://kotlinlang.org", url.value)
    }
}

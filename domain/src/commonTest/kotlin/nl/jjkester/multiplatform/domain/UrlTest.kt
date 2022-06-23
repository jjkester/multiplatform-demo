package nl.jjkester.multiplatform.domain

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class UrlTest {

    @Test
    fun testUrlConstructor() {
        val url = Url("https://kotlinlang.org")

        val result = url.value

        assertThat(result)
            .isEqualToOneOf(
                "https://kotlinlang.org",
                "https://kotlinlang.org/"
            )
    }

    @Test
    fun testUrlSerialization() {
        val url = Url("https://kotlinlang.org")

        val result = Json.encodeToString(url)

        assertThat(result)
            .isEqualToOneOf(
                """"https://kotlinlang.org"""",
                """"https://kotlinlang.org/""""
            )
    }

    @Test
    fun testUrlDeserialization() {
        val json = """"https://kotlinlang.org""""

        val result = Json.decodeFromString<Url>(json)

        assertThat(result)
            .prop(Url::value)
            .isEqualToOneOf(
                "https://kotlinlang.org",
                "https://kotlinlang.org/"
            )
    }

    companion object {
        private fun <T> Assert<T>.isEqualToOneOf(vararg expected: T) = given { actual ->
            if (expected.any { actual == it }) return
            expected("one of:${show(expected)} but was:${show(actual)}")
        }
    }
}

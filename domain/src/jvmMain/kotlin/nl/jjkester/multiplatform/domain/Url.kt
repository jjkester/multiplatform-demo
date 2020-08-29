package nl.jjkester.multiplatform.domain

import kotlinx.serialization.Serializable
import java.net.URL

/**
 * JVM Url implementation.
 *
 * @property platformValue Backing platform URL object.
 */
@Serializable(UrlSerializer::class)
actual data class Url(val platformValue: URL) {

    /**
     * String value of this Url.
     */
    actual val value: String
        get() = platformValue.toString()

    /**
     * Creates a new [Url] object with the given value.
     *
     * @param value String representation of the [URL].
     */
    @Suppress("ConvertSecondaryConstructorToPrimary")
    actual constructor(value: String) : this(URL(value))
}

/**
 * Wraps this [URL] to use as [Url] domain object.
 */
fun URL.asDomain() = Url(this)

/**
 * Creates a new [Url] domain object with the same value as this [URL].
 */
fun URL.toDomain() = Url(this.toString())

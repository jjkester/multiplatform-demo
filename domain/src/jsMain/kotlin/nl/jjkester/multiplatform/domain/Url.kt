package nl.jjkester.multiplatform.domain

import kotlinx.serialization.Serializable
import org.w3c.dom.url.URL

/**
 * JavaScript Url implementation.
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
     * @param value String representation of the URL.
     */
    actual constructor(value: String) : this(URL(value))
}

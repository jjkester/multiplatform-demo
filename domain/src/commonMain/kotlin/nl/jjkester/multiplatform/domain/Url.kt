package nl.jjkester.multiplatform.domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Multi-platform specification of a URL.
 *
 * Actual implementations are expected to wrap a platform native URL object as truth. The string value should be
 * computed from this object. The string constructor should create a platform native object from the provided string.
 */
@Serializable(UrlSerializer::class)
expect class Url {

    /**
     * String value of this Url.
     */
    val value: String

    /**
     * Creates a new [Url] object with the given value.
     *
     * @param value String representation of the Url.
     */
    @Suppress("ConvertSecondaryConstructorToPrimary") // Primary constructor defined in actual classes
    constructor(value: String)
}

object UrlSerializer : KSerializer<Url> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Url", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Url) = encoder.encodeString(value.value)

    override fun deserialize(decoder: Decoder): Url = Url(decoder.decodeString())
}

package nl.jjkester.multiplatform.client

/**
 * Configuration for the client.
 *
 * @property host Host where the server is running.
 * @property port Port to which the server is listening.
 */
data class ClientConfig(val host: String, val port: Int)

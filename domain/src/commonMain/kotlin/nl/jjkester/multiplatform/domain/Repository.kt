package nl.jjkester.multiplatform.domain

import kotlinx.serialization.Serializable

/**
 * Repository on GitHub.
 *
 * @property owner Name of the user or organization that owns the repository.
 * @property name Name of the repository.
 */
@Serializable
data class Repository(val owner: String, val name: String)

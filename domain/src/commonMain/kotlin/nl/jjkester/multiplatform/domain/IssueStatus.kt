package nl.jjkester.multiplatform.domain

import kotlinx.serialization.Serializable

/**
 * Status of a GitHub issue.
 */
@Serializable
enum class IssueStatus {
    OPEN, CLOSED
}

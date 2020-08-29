package nl.jjkester.multiplatform.domain

import kotlinx.serialization.Serializable

/**
 * Issue for a repository on GitHub.
 *
 * @property number Issue number.
 * @property title Short description of the issue.
 * @property status Status of the issue.
 * @property link Link to the human-readable version of the issue.
 */
@Serializable
data class Issue(val number: Int, val title: String, val status: IssueStatus, val link: Url)

/**
 * Whether the issue is open.
 */
val Issue.isOpen: Boolean
    get() = status == IssueStatus.OPEN

/**
 * Whether the issue is closed.
 */
val Issue.isClosed: Boolean
    get() = status == IssueStatus.CLOSED

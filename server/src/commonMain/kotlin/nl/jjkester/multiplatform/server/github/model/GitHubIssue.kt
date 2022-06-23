package nl.jjkester.multiplatform.server.github.model

import kotlinx.serialization.Serializable
import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.IssueStatus
import nl.jjkester.multiplatform.domain.Url

@Serializable
internal data class GitHubIssue(val number: Int, val state: String, val title: String, val html_url: String)

internal fun GitHubIssue.toDomain() = Issue(number, title, state.parseIssueStatus(), Url(html_url))

private fun String.parseIssueStatus() = when (this) {
    "open" -> IssueStatus.OPEN
    "closed" -> IssueStatus.CLOSED
    else     -> throw IllegalStateException("Unknown issue status: $this")
}

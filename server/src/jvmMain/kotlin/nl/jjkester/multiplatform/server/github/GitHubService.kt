package nl.jjkester.multiplatform.server.github

import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.Repository
import nl.jjkester.multiplatform.server.github.model.GitHubIssue
import nl.jjkester.multiplatform.server.github.model.toDomain
import org.slf4j.LoggerFactory

/**
 * Service for retrieving data from GitHub.
 *
 * @param client GitHub client to use.
 */
internal class GitHubService(private val client: GitHubClient) {

    private val log = LoggerFactory.getLogger(GitHubService::class.java)

    /**
     * Returns the issues for the given repository.
     *
     * @param repository Repository to find issues for.
     * @return List of issues for the given repository.
     */
    suspend fun getIssues(repository: Repository): List<Issue> {
        try {
            val result = client.getRepositoryIssues(repository.owner, repository.name).map(GitHubIssue::toDomain)
            log.debug("Retrieved issues: {}", result)
            return result
        } catch (ex: Exception) {
            log.error("Error retrieving issues: {}", ex.message)
            throw ex
        }
    }
}

package nl.jjkester.multiplatform.server.github

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import nl.jjkester.multiplatform.server.github.model.GitHubIssue

/**
 * Client for the GitHub API.
 *
 * @param http Http client to use.
 */
internal class GitHubClient(private val http: HttpClient) {

    /**
     * Retrieves the issues for the given repository.
     *
     * @param owner Owner of the repository.
     * @param repository Repository to get the issues for.
     * @return List of issues for the given repository.
     */
    suspend fun getRepositoryIssues(owner: String, repository: String): List<GitHubIssue> {
        val urlString = "https://api.github.com/repos/${owner.encodeURLPath()}/${repository.encodeURLPath()}/issues"

        Napier.d("Fetching issues: $urlString")

        val result: List<GitHubIssue> = http.get(urlString) {
            header("Accept", "application/vnd.github.v3+json")
            parameter("state", "all")
            parameter("per_page", 100)
            accept(ContentType.Application.Json)
        }.body()

        Napier.d("Fetched issues: ${result.count()}")

        return result
    }
}

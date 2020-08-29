package nl.jjkester.multiplatform.client

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.Repository

class Client(private val http: HttpClient, private val config: ClientConfig) {

    suspend fun getIssues(repository: Repository): List<Issue> {
        val urlString = "http://${config.host}:${config.port}/"

        return http.post(urlString) {
            body = repository
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}

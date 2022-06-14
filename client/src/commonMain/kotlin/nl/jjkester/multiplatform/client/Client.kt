package nl.jjkester.multiplatform.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.Repository

class Client(private val http: HttpClient, private val config: ClientConfig) {

    suspend fun getIssues(repository: Repository): List<Issue> {
        val urlString = "http://${config.host}:${config.port}/"

        val response = http.post(urlString) {
            contentType(ContentType.Application.Json)
            setBody(repository)
            accept(ContentType.Application.Json)
        }

        return response.takeIf { it.status.isSuccess() }?.body() ?: throw ClientException
    }
}

object ClientException : Exception("Unable to fetch results")

package nl.jjkester.multiplatform.server

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import nl.jjkester.multiplatform.domain.Repository
import nl.jjkester.multiplatform.server.github.GitHubClient
import nl.jjkester.multiplatform.server.github.GitHubService

fun main() {
    val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json(DefaultJson) {
                ignoreUnknownKeys = true
            })
        }
    }

    val gitHub = GitHubService(GitHubClient(client))

    val server = embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
            })
        }
        install(DefaultHeaders) {
            header(HttpHeaders.AccessControlAllowOrigin, "*")
            header(HttpHeaders.AccessControlAllowHeaders, "*")
        }
        routing {
            options("/") {
                call.respond("")
            }
            post("/") {
                val repository: Repository = call.receive()
                val result = gitHub.getIssues(repository)
                call.respond(result)
            }
        }
    }
    server.start(true)
}

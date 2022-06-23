package nl.jjkester.multiplatform.server

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import nl.jjkester.multiplatform.domain.Repository
import nl.jjkester.multiplatform.server.github.GitHubClient
import nl.jjkester.multiplatform.server.github.GitHubService

fun main() {
    Napier.base(DebugAntilog("server"))

    val client = HttpClient(OkHttp) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(Json(DefaultJson) {
                ignoreUnknownKeys = true
            })
        }
    }

    val gitHub = GitHubService(GitHubClient(client))

    val server = embeddedServer(Netty, 8080) {
        install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
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

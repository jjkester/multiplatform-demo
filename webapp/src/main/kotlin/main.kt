import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.document
import nl.jjkester.multiplatform.client.Client
import nl.jjkester.multiplatform.client.ClientConfig
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement

fun main() {
    val http = HttpClient(Js) {
        install(ContentNegotiation) {
            json()
        }
    }
    val client = Client(http, ClientConfig("localhost", 8080))
    val presenter = IssuesPresenter(client)
    val page = IssuesPage(presenter)

    document.onreadystatechange = {
        presenter.attach(page)
        val form = document.getElementById("form") as HTMLFormElement
        form.onsubmit = { event ->
            event.preventDefault()
            val owner = document.getElementById("input-owner") as HTMLInputElement
            val repo = document.getElementById("input-repo") as HTMLInputElement
            presenter.loadIssues(owner.value, repo.value)
            false
        }
        Unit
    }
}

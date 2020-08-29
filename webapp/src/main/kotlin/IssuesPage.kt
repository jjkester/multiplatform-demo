import kotlinx.browser.document
import nl.jjkester.multiplatform.domain.Issue
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLFormElement

class IssuesPage(private val presenter: IssuesContract.Presenter) : IssuesContract.View {

    private val loader get() = document.getElementById("loader") as HTMLElement
    private val results get() = document.getElementById("results") as HTMLElement
    private val issueList get() = document.getElementById("issue-list") as HTMLElement
    private val noResults get() = document.getElementById("no-results") as HTMLElement
    private val error get() = document.getElementById("error") as HTMLElement
    private val errorMessage get() = document.getElementById("error-message") as HTMLElement

    override fun set(state: IssuesContract.ViewState) {
        when (state) {
            IssuesContract.ViewState.Initial    -> {
                loader.hide()
                results.hide()
                noResults.show()
                error.hide()
            }
            IssuesContract.ViewState.Loading    -> {
                loader.show()
                results.hide()
                noResults.hide()
                error.hide()
            }
            is IssuesContract.ViewState.Error   -> {
                loader.hide()
                results.hide()
                noResults.hide()
                error.show()
                errorMessage.innerText = state.message ?: "Unknown error"
            }
            is IssuesContract.ViewState.Results -> {
                loader.hide()
                results.show()
                issueList.clear()
                state.issues.forEach { issue ->
                    val row = IssueTemplate().run {
                        bind(issue)
                        get()
                    }
                    issueList.appendChild(row)
                }
                noResults.hide()
                error.hide()
            }
        }
    }

    private fun HTMLElement.show() {
        style.display = "block"
    }

    private fun HTMLElement.hide() {
        style.display = "none"
    }

    private fun HTMLElement.clear() {
        innerHTML = ""
    }
}

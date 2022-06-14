import io.ktor.client.features.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.jjkester.multiplatform.client.Client
import nl.jjkester.multiplatform.domain.Repository
import kotlin.coroutines.CoroutineContext

class IssuesPresenter(private val client: Client) : IssuesContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var view: IssuesContract.View

    override fun attach(view: IssuesContract.View) {
        this.view = view
        view.set(IssuesContract.ViewState.Initial)
    }

    override fun loadIssues(owner: String, repo: String) {
        view.set(IssuesContract.ViewState.Loading)

        launch {
            try {
                val issues = client.getIssues(Repository(owner, repo))
                view.set(IssuesContract.ViewState.Results(issues))
            } catch (ex: ResponseException) {
                view.set(IssuesContract.ViewState.Error(ex.toString()))
            }
        }
    }
}

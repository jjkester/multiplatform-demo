import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.Repository

interface IssuesContract {
    interface View {
        fun set(state: ViewState)
    }

    interface Presenter {
        fun attach(view: View)
        fun loadIssues(owner: String, repo: String)
    }

    sealed class ViewState {
        object Initial : ViewState()
        object Loading : ViewState()
        data class Error(val message: String?) : ViewState()
        data class Results(val issues: List<Issue>) : ViewState()
    }
}

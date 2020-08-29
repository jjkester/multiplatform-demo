import kotlinx.browser.document
import kotlinx.dom.addClass
import nl.jjkester.multiplatform.domain.Issue
import nl.jjkester.multiplatform.domain.IssueStatus
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLLIElement
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLSpanElement

class IssueTemplate {

    private val containerElem = document.createElement("li").apply {
        addClass("issue")
    } as HTMLLIElement
    private val numberElem = document.createElement("span").apply {
        addClass("issue-number")
    }.also {
        containerElem.appendChild(it)
    } as HTMLSpanElement
    private val titleElem = document.createElement("h3").apply {
        addClass("issue-title")
    }.also {
        containerElem.appendChild(it)
    } as HTMLHeadingElement
    private val statusElem = document.createElement("span").apply {
        addClass("issue-status")
    }.also {
        containerElem.appendChild(it)
    } as HTMLSpanElement
    private val linkContainingElem = document.createElement("span").apply{
        addClass("issue-link")
    }.also {
        containerElem.appendChild(it)
    }
    private val linkElem = document.createElement("a").also {
        linkContainingElem.appendChild(it)
    } as HTMLAnchorElement

    fun bind(issue: Issue) {
        numberElem.innerText = issue.number.toString()
        titleElem.innerText = issue.title
        statusElem.apply {
            val (text, extraClass) = when (issue.status) {
                IssueStatus.OPEN   -> "open" to "issue-status-open"
                IssueStatus.CLOSED -> "closed" to "issue-status-closed"
            }
            innerText = text
            addClass(extraClass)
        }
        linkElem.href = issue.link.value
        linkElem.innerText = "View"
    }

    fun get(): HTMLElement = containerElem
}

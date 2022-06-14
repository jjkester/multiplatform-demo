package nl.jjkester.multiplatform.cli;

import io.ktor.client.HttpClient;
import io.ktor.client.HttpClientKt;
import io.ktor.client.engine.okhttp.OkHttp;
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation;
import io.ktor.http.ContentType;
import io.ktor.serialization.Configuration;
import io.ktor.serialization.kotlinx.json.JsonSupportKt;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import nl.jjkester.multiplatform.client.Client;
import nl.jjkester.multiplatform.client.ClientConfig;
import nl.jjkester.multiplatform.domain.Issue;
import nl.jjkester.multiplatform.domain.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String[] args) {
        // Use Ktor HTTP client as resource
        try (final HttpClient http = createHttpClient()) {
            // Set up client
            final ClientConfig config = new ClientConfig("localhost", 8080);
            final Client client = new Client(http, config);

            // Get arguments
            if (args.length != 2) {
                System.err.println("Usage: <user> <repository>");
                System.exit(-1);
            }
            final Repository repository = new Repository(args[0], args[1]);

            // Call client in blocking coroutine context
            final List<Issue> issues = BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, (coroutineScope, continuation) -> client.getIssues(repository, continuation));

            // Print issues
            final StringBuilder out = new StringBuilder();
            out.append(String.format("Found %d issues for repository %s/%s:%n", issues.size(), repository.getOwner(), repository.getName()));
            out.append(formatIssuesForPrinting(issues));
            System.out.print(out);
        } catch (Exception e) {
            System.err.println("Issues could not be retrieved: " + e.getMessage());
        }
    }

    private static HttpClient createHttpClient() {
        return HttpClientKt.HttpClient(OkHttp.INSTANCE, okHttpConfig -> {
            okHttpConfig.setExpectSuccess(true);
            okHttpConfig.install(ContentNegotiation.Plugin, (Configuration contentNegotiationConfig) -> {
                JsonSupportKt.json(contentNegotiationConfig, JsonSupportKt.getDefaultJson(), ContentType.Application.INSTANCE.getJson());
                return Unit.INSTANCE;
            });
            return Unit.INSTANCE;
        });
    }

    public static String formatIssuesForPrinting(final List<Issue> issues) {
        final int numberLength = issues.stream()
                .map(issue -> Integer.toString(issue.getNumber()).length())
                .reduce(0, Math::max);
        final int statusLength = issues.stream()
                .map(issue -> issue.getStatus().name().length())
                .reduce(0, Math::max);

        final String template = String.format("- #%%0%dd [%%-%ds] %%s%n", numberLength, statusLength);

        return issues.stream()
                .map(issue -> String.format(template, issue.getNumber(), issue.getStatus().name(), issue.getTitle()))
                .collect(Collectors.joining());
    }
}

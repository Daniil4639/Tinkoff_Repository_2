package edu.java.clients;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Objects;

public class GitHubClient extends Client{

    public GitHubClient(String url) {
        super(Objects.requireNonNullElse(url, "https://api.github.com/"));
    }

    public void getJson() {
        Mono<Object[]> response = client.get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Object[].class).log();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://github.com/Daniil4639/Tinkoff_Repository_2/pull/1"))
            .GET()
            .build();

        try {
            HttpClient client1 = HttpClient.newHttpClient();

            client1.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get("newJSON.json")));
        }
        catch (Exception e) {
            System.out.println("bruh");
        }
    }
}

package ru.yandex.practicum.filmorate.modeltest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import ru.yandex.practicum.filmorate.FilmorateApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {

    public static HttpClient client;
    public static URI uri;

    @BeforeAll
    public static void run() {
        SpringApplication.run(FilmorateApplication.class);
        client = HttpClient.newHttpClient();
        uri = URI.create("http://localhost:8080/films");
    }

    @Test
    public void nameCantBeEmpty() throws IOException, InterruptedException {
        String json = "{" +
                "  \"name\": \"\"," +
                "  \"description\": \"description\"," +
                "  \"releaseDate\": \"2000-01-01\"," +
                "  \"duration\": 20\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void descriptionShouldNotBeLongerThan200Char() throws IOException, InterruptedException {
        String json = "{" +
                "  \"name\": \"name\"," +
                "  \"description\": \"......................................................." +
                "............................................................................" +
                ".......................................................................\"," +
                "  \"releaseDate\": \"2000-01-01\"," +
                "  \"duration\": 20\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void releaseDateCantBeEarlierThan1895() throws IOException, InterruptedException {
        String json = "{" +
                "  \"name\": \"name\"," +
                "  \"description\": \"description\"," +
                "  \"releaseDate\": \"1890-01-01\"," +
                "  \"duration\": 20\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void durationMustBePositive() throws IOException, InterruptedException {
        String json = "{" +
                "  \"name\": \"name\"," +
                "  \"description\": \"description\"," +
                "  \"releaseDate\": \"2000-01-01\"," +
                "  \"duration\": -20\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 400);
    }
}
/*
        название не может быть пустым;
        максимальная длина описания — 200 символов;
        дата релиза — не раньше 28 декабря 1895 года;
        продолжительность фильма должна быть положительной.*/

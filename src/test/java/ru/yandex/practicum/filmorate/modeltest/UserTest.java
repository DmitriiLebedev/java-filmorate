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

public class UserTest {

    public static HttpClient client;
    public static URI uri;

    @BeforeAll
    public static void run() {
        SpringApplication.run(FilmorateApplication.class);
        client = HttpClient.newHttpClient();
        uri = URI.create("http://localhost:8080/users");
    }

    @Test
    public void emailCantBeEmpty() throws IOException, InterruptedException {
        String json = "{" +
                "  \"login\": \"login\"," +
                "  \"name\": \"name\"," +
                "  \"email\": \"\"," +
                "  \"birthday\": \"1980-08-20\"" +
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
    public void emailShouldContainAddressSign() throws IOException, InterruptedException {
        String json = "{" +
                "  \"login\": \"login\"," +
                "  \"name\": \"name\"," +
                "  \"email\": \"foo.gmail.com\"," +
                "  \"birthday\": \"1980-08-20\"" +
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
    public void loginCantBeEmpty() throws IOException, InterruptedException {
        String json = "{" +
                "  \"login\": \"\"," +
                "  \"name\": \"name\"," +
                "  \"email\": \"foo@gmail.com\"," +
                "  \"birthday\": \"1980-08-20\"" +
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
    public void dateOfBirthShouldBeInPast() throws IOException, InterruptedException {
        String json = "{" +
                "  \"login\": \"login\"," +
                "  \"name\": \"name\"," +
                "  \"email\": \"foo@gmail.com\"," +
                "  \"birthday\": \"2222-08-20\"" +
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
        электронная почта не может быть пустой и должна содержать символ @;
        логин не может быть пустым и содержать пробелы;
        имя для отображения может быть пустым — в таком случае будет использован логин;
        дата рождения не может быть в будущем.*/

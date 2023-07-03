package org.example.java11;

import lombok.SneakyThrows;
import org.junit.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program interview
 * @author Ben
 * @date 2023-06-29 14:37
 */
public class HttpClientTest {

  @SneakyThrows
  @Test
  public void test() {
    HttpRequest request =
        HttpRequest.newBuilder(URI.create("https://www.baidu.com"))
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.statusCode());
    System.out.println(response.body());
  }

  @Test
  public void test1() throws ExecutionException, InterruptedException {
    HttpRequest request =
        HttpRequest.newBuilder(URI.create("https://www.baidu.com"))
            .version(HttpClient.Version.HTTP_1_1)
            .build();
    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture =
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    HttpResponse<String> response = httpResponseCompletableFuture.get();
    System.out.println(response.statusCode());
    System.out.println(response.body());
  }
}

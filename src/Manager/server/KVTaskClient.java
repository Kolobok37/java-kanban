package Manager.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String url;
    private String authToken;

    public KVTaskClient(String url) {
        this.url="http://"+url+":8078/";
    }

    public void register() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Can't do save request, status code: " + response.statusCode());
            }
            authToken = response.body();
            System.out.println(authToken);

        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка: "+ e.getMessage());
        }
    }

    public String  load(String key) throws IOException,InterruptedException{ // tasks, epics, subtasks , history
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "load/"+key+"?API_TOKEN="+authToken))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Код ответа: " + response.statusCode());
        return response.body();
    }

    public void put(String key, String value) throws IOException,InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "save/"+key+"?API_TOKEN="+authToken))
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
package Manager.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//import exception.ManagerSaveException;

public class KVTaskClient {
    private final String url;
    private String authToken;

    public KVTaskClient(String url) {
        this.url=url;
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
                System.out.println("Ошибка");
                //throw new ManagerSaveException("Can't do save request, status code: " + response.statusCode());
            }
            authToken = response.body();
            System.out.println(authToken);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка");
            //throw new ManagerSaveException("Can't do save request", e);
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
        //System.out.println("Тело ответа: " + response.body());
        return response.body();
    }

    public void save(String key, String value) throws IOException,InterruptedException{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "save/"+key+"?API_TOKEN="+authToken))
                .POST(HttpRequest.BodyPublishers.ofString(value))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
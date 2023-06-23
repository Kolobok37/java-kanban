package Manager.server;

import Manager.Manager;
import Manager.TaskManager.FileBackedTasksManager;
import Tasks.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;

import static java.nio.charset.StandardCharsets.UTF_8;


public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private FileBackedTasksManager manager;
    Gson gson;

    public HttpTaskServer(File file) throws IOException {
        manager = new FileBackedTasksManager(Manager.getDefaultHistory(),
                file);
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        gson = GsonCreate.createGson();

        server.createContext("/tasks/singletask", this::getSingleTask);
        server.createContext("/tasks/task", this::getTask);
        server.createContext("/tasks/epic", this::getEpic);
        server.createContext("/tasks/subtask", this::getSubtask);
        server.createContext("/tasks/history", this::getHistory);
        server.createContext("/tasks", this::getPrioritizedTasks);

    }

    private void getPrioritizedTasks(HttpExchange h) {
        try {
        System.out.println("\n/PrioritizedTasks");
        if ("GET".equals(h.getRequestMethod())) {
            String body = gson.toJson(manager.getPrioritizedTasks());
            sendText(h, gson.toJson(body));
        }
    } catch (Exception e) {
        System.out.println("Ошибка: " + e.getMessage());
        e.printStackTrace();
    } finally {
        h.close();
    }
}

    private void getHistory(HttpExchange h){
    try {
        System.out.println("\n/History");
        if ("GET".equals(h.getRequestMethod())) {
            String body = gson.toJson(manager.getHistoryMemory());
            sendText(h, gson.toJson(body));
        }
    } catch (Exception e) {
        System.out.println("Ошибка: " + e.getMessage());
        e.printStackTrace();
    } finally {
        h.close();
    }
}

    private void getSingleTask(HttpExchange h) {
        try {
            System.out.println("\n/SingelTask");
            if ("GET".equals(h.getRequestMethod())) {
                String body;
                String rawQuery = h.getRequestURI().getRawQuery();
                if (rawQuery.contains("id=")) {
                    int id = Integer.parseInt(rawQuery.substring(rawQuery.indexOf("id=") + 3));
                    if (!manager.getTask(id).getType().equals(TaskType.SingleTask)) {
                        h.sendResponseHeaders(403, 0);
                    }
                    body = manager.getTask(id).toString();
                } else {
                    body = gson.toJson(manager.getAllTasks().stream().filter(task -> task.getType().
                            equals(TaskType.SingleTask)).collect(Collectors.toList()).toString());
                }
                sendText(h, gson.toJson(body));
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            h.close();
        }
    }

    private void getEpic(HttpExchange h) {
        try {
            System.out.println("\n/epic");
            if ("GET".equals(h.getRequestMethod())) {
                String body;
                String rawQuery = h.getRequestURI().getRawQuery();
                if (rawQuery.contains("id=")) {
                    int id = Integer.parseInt(rawQuery.substring(rawQuery.indexOf("id=") + 3));
                    if (!manager.getTask(id).getType().equals(TaskType.Epic)) {
                        h.sendResponseHeaders(403, 0);
                    }
                    body = manager.getTask(id).toString();
                } else {
                    body = gson.toJson(manager.getAllTasks().stream().filter(task -> task.getType().
                            equals(TaskType.Epic)).collect(Collectors.toList()).toString());
                }
                sendText(h, gson.toJson(body));
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            h.close();
        }
    }

    private void getSubtask(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/subtask");
            if ("GET".equals(h.getRequestMethod())) {
                String body;
                String rawQuery = h.getRequestURI().getRawQuery();
                if (rawQuery.contains("id=")) {
                    int id = Integer.parseInt(rawQuery.substring(rawQuery.indexOf("id=") + 3));
                    if (!manager.getTask(id).getType().equals(TaskType.Subtask)) {
                        h.sendResponseHeaders(403, 0);
                    }
                    body = manager.getTask(id).toString();
                } else {
                    body = gson.toJson(manager.getAllTasks().stream().filter(task -> task.getType().
                            equals(TaskType.Subtask)).collect(Collectors.toList()).toString());
                }
                sendText(h, gson.toJson(body));
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            h.close();
        }
    }

    private void getTask(HttpExchange h) throws IOException {
        try {
            System.out.println("\n/task");
            if ("GET".equals(h.getRequestMethod())) {
                String body;
                String rawQuery = h.getRequestURI().getRawQuery();
                if (rawQuery.contains("id=")) {
                    int id = Integer.parseInt(rawQuery.substring(rawQuery.indexOf("id=") + 3));
                    System.out.println(id);
                    body = manager.getTask(id).toString();
                } else {
                    body = manager.getAllTasks().toString();
                }
                sendText(h, gson.toJson(body));
            } else if ("POST".equals(h.getRequestMethod())) {
                String value = readText(h);
                if (value.isEmpty()) {
                    System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                    h.sendResponseHeaders(400, 0);
                    return;
                }
                if (value.contains("\"type\":\"SingleTask\"")) {
                    SingleTask task = gson.fromJson(value, SingleTask.class);
                    manager.setTask(task);
                } else if (value.contains("\"type\":\"Epic\"")) {
                    Epic task = gson.fromJson(value, Epic.class);
                    manager.setTask(task);
                } else if (value.contains("\"type\":\"Subtask\"")) {
                    Subtask task = gson.fromJson(value, Subtask.class);
                    manager.setTask(task);
                }
                h.sendResponseHeaders(200, 0);
            } else if ("DELETE".equals(h.getRequestMethod())) {
                String rawQuery = h.getRequestURI().getRawQuery();
                if (rawQuery.contains("id=")) {
                    int id = Integer.parseInt(rawQuery.substring(rawQuery.indexOf("id=") + 3));
                    manager.removeTask(id);
                } else {
                    manager.clearAll();
                }
                System.out.println(manager.getAllTasks());
            } else {
                System.out.println("Ждём POST,GET или DELETE запрос, а получил: " + h.getRequestMethod());
                h.sendResponseHeaders(405, 0);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            h.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new HttpTaskServer(new File("TaskManager")).start();
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }


    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

}
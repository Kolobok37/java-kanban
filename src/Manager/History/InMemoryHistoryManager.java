package Manager.History;

import Tasks.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryHistoryManager implements HistoryManager {
    HashMap<Integer, Node> nodeHashMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task newTask) {
        Node newNode = new Node(newTask);
        if (nodeHashMap.containsKey(newTask.getId())) {
            remove(newTask.getId());
        }
        nodeHashMap.put(newTask.getId(), newNode);
        if (head == null) {
            head = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
        }
        tail = newNode;
    }

    @Override
    public LinkedList<Task> getHistory() {
        LinkedList<Task> tasks = new LinkedList<>();
        Node nodeAdded;
        if (!nodeHashMap.isEmpty()) {
            nodeAdded = head;
            while (nodeAdded != null) {
                tasks.add(nodeAdded.getTask());
                nodeAdded = nodeAdded.next;
            }
        }
        return tasks;
    }

    public List<Integer> getHistoryId(){
        List<Integer> historyId = new LinkedList<>();
        historyId = getHistory().stream().map(task -> task.getId()).collect(Collectors.toList());
        return historyId;
    }

    @Override
    public void remove(int id) {
        Node nodeRemoving = nodeHashMap.get(id);
        if (!nodeHashMap.containsKey(id)) {
            return;
        }
        if (nodeRemoving.equals(head)) {
            if (nodeRemoving.equals(tail)) {
                head = null;
                tail = null;
            } else {
                head = nodeRemoving.next;
                nodeRemoving.next.previous = null;
            }
        } else if (nodeRemoving.equals(tail)) {
            tail = nodeRemoving.previous;
            nodeRemoving.previous.next = null;
        } else {
            nodeRemoving.next.previous = nodeRemoving.previous;
            nodeRemoving.previous.next = nodeRemoving.next;
        }
        nodeHashMap.remove(id);
    }

    public void clear() {
        head = null;
        tail = null;
        nodeHashMap.clear();
    }
}

class Node<T extends Task> {
    public Node<T> next;
    public Node<T> previous;
    Task task;

    public Node(Task task) {
        this.task = task;

    }

    public Task getTask() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(next, node.next) && Objects.equals(previous, node.previous) && Objects.equals(task, node.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(next, previous, task);
    }
}

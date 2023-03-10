package Manager.History;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    HashMap<Integer, Node> nodeHashMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    @Override
    public void addTaskInHistory(Task newTask) {
        Node newNode = new Node(newTask);
        if (nodeHashMap.containsKey(newTask.getId())) {
            remove(newTask.getId());
        }
        nodeHashMap.put(newTask.getId(), newNode);
        if (tail == null) {
            tail = newNode;
            head = newNode;
        } else {
            newNode.previous = head;
            head.next = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public LinkedList<Task> getHistory() {
        LinkedList<Task> tasks = new LinkedList<>();
        Node nodeAdded;
        if (!nodeHashMap.isEmpty()){
            nodeAdded=tail;
            while(true){
                tasks.add(nodeAdded.getTask());
                if(nodeAdded.next==null){
                    break;
                }
                nodeAdded = nodeAdded.next;
            }
        }

        return tasks;
    }

    @Override
    public void remove(int id) {//
        if (nodeHashMap.containsKey(id)) {
            if (nodeHashMap.get(id).equals(head)) {
                if (nodeHashMap.get(id).equals(tail)) {
                    nodeHashMap.remove(id);
                } else {
                    head = nodeHashMap.get(id).previous;
                    nodeHashMap.get(id).previous.next = null;
                    nodeHashMap.remove(id);
                }
            } else if (nodeHashMap.get(id).equals(tail)) {
                tail = nodeHashMap.get(id).next;
                nodeHashMap.get(id).next.previous = null;
                nodeHashMap.remove(id);
            } else {
                nodeHashMap.get(id).next.previous = nodeHashMap.get(id).previous;
                nodeHashMap.get(id).previous.next = nodeHashMap.get(id).next;
                nodeHashMap.remove(id);
            }
        }
    }

    public void clear(){
        head = null;
        tail=null;
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
}

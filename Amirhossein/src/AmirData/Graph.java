package AmirData;

import java.util.*;
import java.util.Stack;

public class Graph {
    private class Node {
        private String label;

        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // We want to store this Node somewhere in this graph
    // we use hash table here cause the item could be end of the list
    // Map here is an interface and HashMap is an implementation of that Interface
    private Map<String, Node> nodes = new HashMap<>();
    // Adjacency list
    // Node is key and Each key can be mapped to a list of nodes
    // using an Array of list for implementing adjacency list
    // Here we are storing the edges that exist
    private Map<Node, List<Node>> adjacencyList = new HashMap<>();

    public void addNode(String label) {
        var node = new Node(label);
        // we pass the label as the key and node object as the value
        nodes.putIfAbsent(label, node);
        // Once we add a node, it's good to add an entry in the adjacency list as well
        // We pass the node as the key and the new ArrayList as the value
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(String from, String to) {
        // we have to make sure the arguments we passed here are valid node in our graph
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalStateException();

        var toNode = nodes.get(to);
        if (toNode == null)
            throw new IllegalStateException();
        // Now we need adjacency list to store the relationship between this two nodes

        adjacencyList.get(fromNode).add(toNode);
    }

    public void print() {
        for (var source : adjacencyList.keySet()) {
            var targets = adjacencyList.get(source);
            if (!targets.isEmpty())
                System.out.println(source + " is connected to " + targets);
        }
    }

    public void removeNode(String label) {
        // make sure this label represent a valid node
        var node = nodes.get(label);
        if (node == null)
            return;

        /* we have to go to our adjacency list, and if each node is connected to with
           that node in (var node) above here, we make sure to remove that connection
         */

        // this going to return all the keys
        for (var n : adjacencyList.keySet())
            // now we call it adjacency list and we are going to remove a target node
            adjacencyList.get(n).remove(node);

        // that's how we remove the node
        adjacencyList.remove(node);
        nodes.remove(node);
    }

    public void removeEdge(String from, String to) {
        var fromNode = nodes.get(from);
        var toNode = nodes.get(to);

        if (fromNode == null || toNode == null)
            return;
        adjacencyList.get(fromNode).remove(toNode);
    }

    public void traverseDepthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;
        // HashSet here is implementation of Set<Node>
        traverseDepthFirst(node, new HashSet<>());
    }

    // we need set for keeping tracking of nodes. and also we call this visited
    private void traverseDepthFirst(Node root, Set<Node> visited) {
        System.out.println(root);
        visited.add(root);

        for (var node : adjacencyList.get(root))
            if (!visited.contains(node))
                traverseDepthFirst(node, visited);
    }

    public void traversDepthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;

        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);

        while (!stack.isEmpty()) {
            var current = stack.pop();

            if (visited.contains(current))
                continue;

            System.out.println(current);
            visited.add(current);

            for (var neighbour : adjacencyList.get(current))
                if (!visited.contains(neighbour))
                    stack.push(neighbour);

        }
    }

    public void traverseBreadthFirst(String root) {
        var node = nodes.get(root);
        if (node == null)
            return;

        // Here we need a set for keeping track of the visited of nodes
        Set<Node> visited = new HashSet<>();

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            var current = queue.remove();

            if (visited.contains(current))
                continue;

            System.out.println(current);
            visited.add(current);

            // Finally we should look at all unvisited neighbor
            for (var neighbor : adjacencyList.get(current))
                if (!visited.contains(neighbor))
                    queue.add(neighbor);

        }
    }

    public List<String> topologicalSort() {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (var node : nodes.values())
        topologicalSort(node, visited, stack);

        List<String> sorted = new ArrayList<>();
        while (!stack.empty())
            sorted.add(stack.pop().label);

        return sorted;
    }

    private void topologicalSort(Node node, Set<Node> visited, Stack<Node> stack) {
        if (visited.contains(node))
            return;

        visited.add(node);

        for (var neighbour : adjacencyList.get(node))
            topologicalSort(neighbour, visited, stack);

        stack.push(node);
    }

    public boolean hasCycle() {
        Set<Node> all = new HashSet<>();
        all.addAll(nodes.values());

        Set<Node> visiting = new HashSet<>();
        Set<Node> visited = new HashSet<>();

        //as long as the first set is not empty
        while (!all.isEmpty()) {
            // we pick a node from it doesn't matter which node
            var current = all.toArray(new Node[0])[0];
            if(hasCycle(current, all, visiting, visited))
                return true;
        }

        return false;
    }

    private boolean hasCycle(Node node, Set<Node> all, Set<Node> visiting,
                             Set<Node> visited) {
        all.remove(node);
        visiting.add(node);

        for (var neighbour : adjacencyList.get(node)) {
            if (visited.contains(neighbour))
                continue;

            if (visiting.contains(neighbour))
                return true;

            if (hasCycle(neighbour, all, visiting, visited))
                return true;
        }

        visiting.remove(node);
        visited.add(node);
        return false;
    }
}

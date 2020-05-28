package AmirData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    public static int ALPHABET_SIZE = 26;

    private class Node {
        private char value;
        private HashMap<Character, Node> children = new HashMap<>();
        private boolean isEndOfWord;

        public Node (char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;
        }

        public boolean hasChild(char ch) {
            return children.containsKey(ch);
        }

        public void addChild(char ch) {
            children.put(ch, new Node(ch));
        }

        public Node getChild(char ch) {
            return children.get(ch);
        }

        public Node[] getChildren() {
            return children.values().toArray(new Node[0]);
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

        public void removeChild(char ch) {
            children.remove(ch);
        }
    }

    private Node root = new Node( ' ');

    public void insert(String word) {
        var current = root;
        for (var ch : word.toCharArray()) {
            // If the current Node doesn't have a child for this character add it and give it to me
            if (!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }

    public boolean contains(String word) {
        if (word == null)
            return false;

        var current = root;
        for (var ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;
            current = current.getChild(ch);
        }
        return current.isEndOfWord;
    }

    public void traverse() {
        traverse(root);
    }

    private void traverse(Node root) {
        for (var child : root.getChildren())
            traverse(child);

        System.out.println(root.value);

    }

    public void remove(String word) {
        if (word == null)
            return;

        remove(root, word, 0);
    }

    private void remove(Node root, String word, int index ) {
        // Base condition (the last letter of this word)
        // Pay attention here we do not write word.length() - 1, cause our root is empty
        if (index == word.length()) {
            // this line remove the end of the word marker
            root.isEndOfWord = false;
            return;
        }


        // What character we are looking at?
        var ch = word.charAt(index);
        var child = root.getChild(ch);
        // It is possible our child node is empty
        if (child == null)
            return;
        // we recursively call the remove method, give it child node and same word and the next index
        remove(child, word, index + 1);

        // in above line we visit each child first and then come back to the root node
        System.out.println(root.value);

        // We have to check to see child has any children?
        // If it's doesn't and it's not the end of another word, we can physically remove this child
        // If child doesn't have any children
        if (!child.hasChildren() && !child.isEndOfWord)
            root.removeChild(ch);

    }

    public List<String> findWords(String prefix) {
        // List is an interface and ArrayList is an implementation of this interface
        List<String> words = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);
        findWords(lastNode, prefix, words);

        return words;
    }

    private void findWords(Node root, String prefix, List<String> words) {
        if (root == null)
            return;

        if (root.isEndOfWord)
            words.add(prefix);

        for (var child : root.getChildren())
            findWords(child, prefix + child.value, words);
    }

    private Node findLastNodeOf(String prefix) {
        if (prefix == null)
            return null;

        var current = root;
        for (var ch : prefix.toCharArray()) {
            var child = current.getChild(ch);
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
}

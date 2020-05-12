package AmirData;

// first give it private class and value and left and right child
public class AVLTree {
    private class AVLNode {
        private int height;
        private int value;
        private AVLNode leftChild;
        private AVLNode rightChild;

        // constructor for initializing Node Object
        public AVLNode(int value) {
            this.value = value;
        }

        // Override the to string, Because I use this in Debugger
        @Override
        public String toString() {
            return "Value=" + this.value;
        }
    }

    // Every Tree needs a reference to it's root Node
    private AVLNode root;

    // Implement the insert method
    // This public method kick off the recursion and a private recursive method
    public void insert(int value) {
        root = insert(root, value);
    }

    private AVLNode insert(AVLNode root, int value) {
        // If our Tree is empty. we are set it to new AVLNode to this value
        if (root == null)
            return new AVLNode(value);
        if (value < root.value)
            // Give it root.leftChild and root.rightChild with this value
            root.leftChild = insert(root.leftChild, value);
        else
            root.rightChild = insert(root.rightChild, value);

        // How we calculate height of a node or root. and we can refactor this line: setHeight(root);
        root.height = Math.max(height(root.leftChild), height(root.rightChild)) + 1;

        return balance(root);

    }

    private AVLNode balance(AVLNode root) {
        // That means is lef heavy
        if (isLeftHeavy(root)) {
            if (balanceFactor(root.leftChild) < 0)
                root.leftChild = rotateLeft(root.leftChild);
            return rotateRight(root);
        }
        else if (isRightHeavy(root)) { // That means is right heavy
            if (balanceFactor(root.rightChild) > 0)
                root.rightChild = rotateRight(root.rightChild);
            return  rotateLeft(root);
        }
        return root;
    }

    private AVLNode rotateLeft(AVLNode root) {
        var newRoot = root.rightChild;

        root.rightChild = newRoot.leftChild;
        newRoot.leftChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private AVLNode rotateRight(AVLNode root) {
        var newRoot = root.leftChild;

        root.leftChild = newRoot.rightChild;
        newRoot.rightChild = root;

        setHeight(root);
        setHeight(newRoot);

        return newRoot;
    }

    private void setHeight(AVLNode node) {
        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
    }

    private boolean isLeftHeavy(AVLNode node) {
        return balanceFactor(node) > 1;
    }

    private boolean isRightHeavy(AVLNode node) {
        return balanceFactor(node) < -1;
    }

    private int balanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.leftChild) - height(node.rightChild);
    }

    private int height(AVLNode node) {
        return (node == null) ? -1 : node.height;
    }
}

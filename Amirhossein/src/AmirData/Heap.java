package AmirData;

public class Heap {
    private int items[] = new int[10];
    // Number of items in our heap
    private int size;

    public void insert(int value) {
        // If the number of items we have(size) equals to maximum capacity of this array
        if (size == items.length)
            throw new IllegalStateException();

        // Store this value in the next available slot.
        items[size++] = value;
        // Finally recall the bubble up method in insert method
        bubbleUp();
    }
    // bubble up items until it's in the right position

    public int remove() {
        if (size == 0)
            throw new IllegalStateException();
        // Move the value of the last node into the root node
        var root = items[0];
        items[0] = items[--size];

        bubbleDown();
        return root;
    }

    private void bubbleDown() {
        // item (root) > children
        var index = 0;
        // That means we have to swap it with one of this children
        while (index <= size && !isValidParent(index)) {
            var largerChildIndex = largerChildIndex(index);
            // Swap the current item with this larger child
            swap(index, largerChildIndex);

            /*Finally reset the index to the larger child index
            because we are going to continuously bubble down this item. if it's smaller than its children*/
            index = largerChildIndex;

        }
    }

    private int largerChildIndex(int index) {
        if (!hasLeftChild(index))
            return index;

        if (!hasRightChild(index))
            return index;

        return (leftChildIndex(index) > rightChildIndex(index)) ?
                leftChildIndex(index) :
                rightChildIndex(index);
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    private boolean isValidParent(int index) {
        if (!hasLeftChild(index))
            return true;

        var isValid = items[index] >= leftChildIndex(index);

        if (!hasRightChild(index))
            isValid &= isValid & items[index] >= rightChildIndex(index);


        return isValid;
    }

    // calculating the index of the left and right children
    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }
    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    private void bubbleUp() {
        // It is index size
        var index = size - 1;
        // index > 0 means we guard the while loop to hesitate out of the index and become a negative number
        while (index > 0 && items[index] > items[parent(index)]) {
            // Swap the items at this index and at the parentIndex
            swap(index, parent(index));
            index = parent(index);

        }
    }
    // Before the second iteration we need to reset the index
    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        // Copy the first item to temp variable
        var temp = items[first];
        // Copy the second item into the first item
        items[first] = items[second];
        items[second] = temp;
    }
}

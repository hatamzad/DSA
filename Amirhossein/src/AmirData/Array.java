package AmirData;

public class Array {
    private int[] items;
    private int count;

    public Array(int length) {
        items = new int[length];
    }

    public void removeAt(int index) {
        //Validate the index
        if(index < 0 || index >= count)
            //write throw an exception error
            throw new IllegalArgumentException();
        //shift the items to the left to fill the hole
        for(int i = index; i < count; i++)
            //Set the item at this index to the item its right side
            //It means the item at index one should be set to at index 2
            items[i] = items[i + 1];
        //Shrink this Array
        count--;
    }

    public void insert (int item) {
        if (items.length == count) {
            int[] newItems = new int[count * 2];
            //If the array is full, resize it

            for (int i = 0; i < count; i++)
                newItems[i] = items[i];
            //copy all the existing items

            items = newItems;
            //set "items" to this new array
        }

        items[count++] = item;
        //Add the new item at the end
    }

    public int indexOf(int item)  {
        //If we find the item, return the index
        for(int i = 0; i < count; i++)
            if(items[i] == item)
                return i;
        //Otherwise we want ot return -1
        return -1;
    }

    public void print () {
        for (int i = 0; i < count; i++)
            System.out.println(items[i]);
    }
}

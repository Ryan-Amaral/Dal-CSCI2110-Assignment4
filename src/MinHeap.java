import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A heap that is minimally oriented, meaning that the element at the top
 * is the minimum value in the heap, and no node has a parent of a higher
 * value than itself.
 * 
 * Based off of Heap.java provided by Sesh Venugopal.
 * 
 * Assumptions/Restrictions:
 * 
 * Noteworthy Features: 
 * 
 * @author Ryan Amaral / Sesh Venugopal
 *
 * @param <T>
 */
public class MinHeap<T extends Comparable<T>> {
    
    
    /**
     * The backing structure of this heap.
     */
    private ArrayList<T> items;
    
    /**
     * Helps with iteration by maintaining a point in the array.
     */
    private int cursor;
    
    /** From Sesh Venugopal
     * Initializes a new instance by setting up storage of given capacity.
     * 
     * @param cap Initial storage capacity.
     */
    public MinHeap(int cap) {
        items = new ArrayList<T>(cap);
        cursor = -1;
    }
    
    /** From Sesh Venugopal
     * Initializes a new instance by setting up storage of default initial capacity. 
     */
    public MinHeap() {
        items = new ArrayList<T>();
        cursor = -1;
    }
    
    /** From Sesh Venugopal
     * Adds the given item to this heap.
     * 
     * @param item Item to be added.
     */
    public void add(T item) {
        items.add(item);
        // start from end because that is where new element is
        siftUp(items.size()-1); 
    }
    
    /** From Sesh Venugopal, modified by Ryan Amaral
     * Sifts up the item at the given index.
     * 
     * @param index Index of item to be sifted up.
     */
    void siftUp(int index) {
        T newest = items.get(index);
        while (index > 0) {
            int pindex = (index-1) / 2; // the index of parent
            T parent = items.get(pindex); // the parent
            // see if parent > child
            if (newest.compareTo(parent) < 0) {
                // put parent value in child node
                items.set(index, parent);
                index = pindex;
            }
            else break; // index is where newest goes
        }
        items.set(index, newest);
    }
    
    /** From Sesh Venugopal, modified by Ryan Amaral
     * Removes the minimum-priority (top) item of this heap.
     * 
     * @return Removed item.
     * @throws NoSuchElementException If this heap is empty.
     */
    public T deleteMin() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        
        T minItem = items.get(0); // first is min
        // remove and store most recently added item
        T lastItem = items.remove(items.size()-1); 
        
        if (items.isEmpty()) {
            // simply return, no sifting needed
            return minItem;
        }
        
        // move last item to vacant spot at top,and sift down 
        items.set(0, lastItem);
        siftDown(0);
        
        return minItem;
    }
    
    /** From Sesh Venugopal, modified by Ryan Amaral
     * Sifts down the item at the given item.
     * 
     * @param index Index of item to be sifted down.
     */
    void siftDown(int index) {
        T me = items.get(index);
        int lindex = 2*index + 1; // left child index
        while (lindex < items.size()) {
            T minChild = items.get(lindex);
            // index of smallest child
            int minIndex = lindex; // initially left
            
            int rindex = lindex + 1; // right child index
            // if has right child
            if (rindex < items.size()) {
                // the right child
                T rightChild = items.get(rindex);
                // see if right child < left child
                if (rightChild.compareTo(minChild) < 0) {
                    // the right child is min, to be sifted to
                    minChild = rightChild;
                    minIndex = rindex;
                }
            }
            // if a child > me
            if (minChild.compareTo(me) < 0) {
                // put smallest up
                items.set(index, minChild);
                // move indices down
                index = minIndex;
                lindex = 2*index + 1;
            }
            else break; // break when no smaller child
        }
        items.set(index, me);
    }
    
    /** From Sesh Venugopal
     * Empties this heap by removing all items. 
     */
    public void clear() {
        items.clear();
    }
    
    /** From Sesh Venugopal
     * Returns the number of items in this heap.
     * 
     * @return Number of items in this heap.
     */
    public int size() {
        return items.size();
    }
    
    /** From Sesh Venugopal
     * Tells whether this heap is empty or not.
     * 
     * @return True if this heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /** From Sesh Venugopal
     * Returns the first (minimum) item in this heap, and sets the iteration cursor to the 
     * first position. 
     * 
     * @return First (minimum) item in this heap, null if list is empty.
     */
    public T first() {
        if (items.size() == 0) return null;
        cursor = 0;
        return items.get(cursor);
    }
    
    /** From Sesh Venugopal
     * Sets the cursor to the next position, and returns the item in this heap at
     * that position. <br> To iterate over this heap, there
     * must be a call to first( ), followed by successive calls to next( ). Iteration
     * is in level-order sequence.
     * 
     * @return Next item in this heap. Null if heap is empty, or cursor is at the 
     *         end of the heap at the time this method is called, i.e. end of heap was
     *         reached. 
     */
    public T next() {
        // cursor will be out of range
        if (cursor < 0 || cursor == (items.size()-1)) {
            return null; // nothing here
        }
        cursor++; // move cursor up to next
        return items.get(cursor); // return the next
    }
}

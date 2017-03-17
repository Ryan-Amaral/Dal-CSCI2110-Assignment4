import java.util.Random;

/**
 * Tests min and max heap implementations, by seeing if they can
 * successfully perform heap sorts.
 * 
 * Assumptions/Restrictions: 
 * - I am making random numbers between 0 and 999 so that the values are
 * easier to compare than if the full int range were used.
 * 
 * Noteworthy Features: 
 * 
 * @author Ryan Amaral
 *
 */
public class testHeaps {

    /**
     * tests the heap sorts. and analyzes some results.
     * @param args
     */
    public static void main(String[] args) {
        
        Random rand = new Random();
        
        final int amount = 30; // the amount of ints

        Heap<Integer> maxHeap = new Heap<Integer>();
        
        // add 30 random ints to max heap
        for(int i = 0; i < amount; i++){
            maxHeap.add(rand.nextInt(1000));
        }
        
        // level order of max heap
        System.out.println("Level Order of Max Heap:");
        Integer heapNode = maxHeap.first();
        int inLevel = 1; // amount in current level
        int counter = 0; // to change inLevel
        while(heapNode != null){
            // print line and change level amount if needed
            if(counter == inLevel){
                System.out.println();
                inLevel *= 2;
                counter = 0;
            }
            System.out.print(heapNode + "   ");
            
            heapNode = maxHeap.next();
            counter++;
        }
        
        System.out.println("\n\nSort from MaxHeap: ");
        // get elements from heap
        for(int i = 0; i < amount; i++){
            System.out.println(maxHeap.deleteMax());
        }
        
        // now to do same thing with min heap
        
        MinHeap<Integer> minHeap = new MinHeap<Integer>();
        
        // add 30 random ints to min heap
        for(int i = 0; i < amount; i++){
            minHeap.add(rand.nextInt(1000));
        }
        
     // level order of max heap
        System.out.println("Level Order of Min Heap:");
        heapNode = minHeap.first();
        inLevel = 1; // amount in current level
        counter = 0; // to change inLevel
        while(heapNode != null){
            // print line and change level amount if needed
            if(counter == inLevel){
                System.out.println();
                inLevel *= 2;
                counter = 0;
            }
            System.out.print(heapNode + "   ");
            
            heapNode = minHeap.next();
            counter++;
        }
        
        System.out.println("\n\nSort from MinHeap: ");
        // get elements from heap
        for(int i = 0; i < amount; i++){
            System.out.println(minHeap.deleteMin());
        }
        
        /** Output and Analysis
         * 
         *  
         * 
         * 
         */
    }

}

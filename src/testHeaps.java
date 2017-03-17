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
        int amountInLevel = 1; // amount in current level
        int curLevel = 1;
        int counter = 0; // to change inLevel
        while(heapNode != null){
            // increment level number
            if(counter == amountInLevel){
                curLevel++;
                amountInLevel *= 2;
                counter = 0;
            }
            System.out.println(curLevel + " - " + heapNode);
            
            heapNode = maxHeap.next();
            counter++;
        }
        
        System.out.println("\nSort from MaxHeap: ");
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
        System.out.println("\nLevel Order of Min Heap:");
        heapNode = minHeap.first();
        amountInLevel = 1; // amount in current level
        curLevel = 1;
        counter = 0; // to change inLevel
        while(heapNode != null){
            // increment level number
            if(counter == amountInLevel){
                curLevel++;
                amountInLevel *= 2;
                counter = 0;
            }
            System.out.println(curLevel + " - " + heapNode);
            
            heapNode = minHeap.next();
            counter++;
        }
        
        System.out.println("\nSort from MinHeap: ");
        // get elements from heap
        for(int i = 0; i < amount; i++){
            System.out.println(minHeap.deleteMin());
        }
        
        /** Output and Analysis
         * Max Heap:
         *  Level Order of Max Heap:
            1 - 878
            2 - 761
            2 - 774
            3 - 752
            3 - 753
            3 - 578
            3 - 677
            4 - 601
            4 - 721
            4 - 582
            4 - 694
            4 - 460
            4 - 348
            4 - 587
            4 - 591
            5 - 81
            5 - 382
            5 - 395
            5 - 331
            5 - 444
            5 - 168
            5 - 131
            5 - 622
            5 - 62
            5 - 64
            5 - 304
            5 - 185
            5 - 67
            5 - 245
            5 - 308
            
            Sort from MaxHeap: 
            878
            774
            761
            753
            752
            721
            694
            677
            622
            601
            591
            587
            582
            578
            460
            444
            395
            382
            348
            331
            308
            304
            245
            185
            168
            131
            81
            67
            64
            62
         * 
         * The level order of the MaxHeap is a little bit similar to the
         * descending order sort that is obtained from the MaxHeap. It
         * is similar by the fact that the first element is always the
         * max. Also, as you proceed through the level order, the numbers
         * follow a general downward trend, though there may be some next
         * numbers that are higher than the previous. Every node in a 
         * level is less than or equal to the biggest node in the level
         * above it.
         * 
         * 
         * Min Heap:
         *  Level Order of Min Heap:
            1 - 28
            2 - 167
            2 - 106
            3 - 468
            3 - 171
            3 - 332
            3 - 176
            4 - 814
            4 - 486
            4 - 288
            4 - 444
            4 - 426
            4 - 401
            4 - 274
            4 - 258
            5 - 983
            5 - 944
            5 - 904
            5 - 558
            5 - 771
            5 - 417
            5 - 672
            5 - 896
            5 - 811
            5 - 434
            5 - 517
            5 - 846
            5 - 697
            5 - 337
            5 - 397
            
            Sort from MinHeap: 
            28
            106
            167
            171
            176
            258
            274
            288
            332
            337
            397
            401
            417
            426
            434
            444
            468
            486
            517
            558
            672
            697
            771
            811
            814
            846
            896
            904
            944
            983
         *
         * The level order of the MinHeap is a little bit similar to the
         * ascending order sort that is obtained from the MinHeap. It
         * is similar by the fact that the first element is always the
         * min. Also, as you proceed through the level order, the numbers
         * follow a general upward trend, though there may be some next
         * numbers that are lower than the previous. Every node in a 
         * level is greater than or equal to the smallest node in the 
         * level above it.
         *
         */
    }

}

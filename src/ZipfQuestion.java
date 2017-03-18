

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.*;

/**
 * Adds all of the words from the "document.txt" text file into the hash
 * map. the entries in the hash map are then sorted to display the 20 most
 * and least common words from the file.
 * 
 * Assumptions/Restrictions:
 * 
 * Noteworthy Features: Used my own merge sort implementation.
 * 
 * @author Ryan Amaral
 *
 */
public class ZipfQuestion {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ZipfQuestion zipf = new ZipfQuestion();
        
        String text = ""; // to store text from file into string
        String[] textTokenized; // tokenized rep of text
        String line; // a line of the file to read
        File file; // the file to get data from
        FileReader fReader;
        BufferedReader bReader;
        
        // word is a string, amount of hits is value
        HashMap<String, Integer> textHash = new HashMap<String, Integer>();
        
        try{
            file = new File("resources/document.txt");
            fReader = new FileReader(file);
            bReader = new BufferedReader(fReader);
            // read each line
            while((line = bReader.readLine()) != null){
                // append to text only keeping text and spaces. to lower case
                // first replace slashes, dashes, and pluses with spaces.
                line = line.replaceAll("(\\/|\\\\|-|\\+)+", " ");
                // only keep alphabeticals and spaces
                text += " " + line.replaceAll("[^a-zA-Z ]", "").toLowerCase();
            }
            fReader.close();
            bReader.close();
        }catch(IOException e){
            System.out.println("Error reading file. Exiting program.");
            System.exit(0);
        }
        
        // tokenize string by splitting at spaces
        textTokenized = text.split("\\s+");
        int newVal; // int to be used to increment
        for(int i = 0; i < textTokenized.length; i++){
            // in hash so get value to increment
            if(textHash.containsKey(textTokenized[i])){
                newVal = textHash.get(textTokenized[i]).intValue() + 1;
            }
            // not in hash so start at 1
            else{
                newVal = 1;
            }
            textHash.put(textTokenized[i], newVal);
        }
        
        // for some odd reason a single empty string gets through every time
        textHash.remove("");

        // the descending sorted word frequencies
        ArrayList<Entry<String, Integer>> sortedWordFreqs = 
                zipf.mergeSortEntries(textHash, false);
        
        // print top 20 words
        System.out.println("20 most frequent words: ");
        for(int i = 0; i < 20; i++){
            System.out.println(sortedWordFreqs.get(i).getKey() + ": " + 
                    sortedWordFreqs.get(i).getValue().toString());
        }
        
        /*// test to check if my sort works, it passed
        for(int i = 0; i < sortedWordFreqs.size() - 1; i++){
            if(sortedWordFreqs.get(i).getValue().intValue() < sortedWordFreqs.get(i+1).getValue().intValue()){
                System.out.println("Sorting is broken"); // wont be printed
            }
        }*/
        
        // store the rank and frequency to a comma separated file.
        // must do while descending sorted
        try {
            zipf.SaveWordFreqsToFile("WordFreqs", sortedWordFreqs);
            System.out.println("\nSuccessfully save data to file.");
        } catch (IOException e) {
            System.out.println("Failed to save data to file.");
        }
        
        // the ascending sorted word frequencies
        sortedWordFreqs = zipf.mergeSortEntries(textHash, true);
        
        // print lowest 20 words
        System.out.println("\n20 least frequent words: ");
        for(int i = 0; i < 20; i++){
            System.out.println(sortedWordFreqs.get(i).getKey() + ": " + 
                    sortedWordFreqs.get(i).getValue().toString());
        }
        
        /*// test to check if my sort works, it passed
        for(int i = 0; i < sortedWordFreqs.size() - 1; i++){
            if(sortedWordFreqs.get(i).getValue().intValue() > sortedWordFreqs.get(i+1).getValue().intValue()){
                System.out.println("Sorting is broken"); // wont be printed
            }
        }*/
        
        /* Results:
         * 
           20 most frequent words: 
            the: 6149
            and: 6000
            of: 4848
            to: 4337
            in: 3078
            a: 2812
            that: 1874
            for: 1560
            or: 1264
            with: 1090
            is: 1086
            are: 1016
            health: 911
            on: 910
            as: 856
            students: 845
            soy: 806
            be: 798
            their: 775
            protein: 734
            
           20 least frequent words: 
            misinterpreted: 1
            feedings: 1
            furumoto: 1
            bites: 1
            distracted: 1
            satisfy: 1
            surya: 1
            grundhauser: 1
            projected: 1
            deliterious: 1
            pickled: 1
            tuna: 1
            nicolini: 1
            isometric: 1
            teachable: 1
            taylor: 1
            versa: 1
            escalate: 1
            begun: 1
            filter: 1
         * 
         * */
        
        
    }
    
    /**
     * Saves the sorted word frequencies to a text file that will be used
     * to generate the graph.
     * @param fileName The name of the file to save to.
     * @param sortedWordFreqs The words and frequencies.
     * @throws IOException 
     */
    public void SaveWordFreqsToFile(String fileName, 
            ArrayList<Entry<String, Integer>> sortedWordFreqs) 
                    throws IOException{
        PrintWriter writer = null;
        try{
            File file = new File("resources/" + fileName + ".csv");
            writer = new PrintWriter(file);
            // first row is all numbers
            for(int i = 0; i < sortedWordFreqs.size(); i++){
                writer.write(((Integer)(i+1)).toString());
                if(i < sortedWordFreqs.size() - 1)
                    writer.write(",");
                else
                    writer.write("\n");
            }
            // second row is all frequencies
            for(int i = 0; i < sortedWordFreqs.size(); i++){
                writer.write(sortedWordFreqs.get(i).getValue().toString());
                if(i < sortedWordFreqs.size() - 1)
                    writer.write(",");
            }
        }finally{
            if(writer != null)
                writer.close();
        }
    }
    
    /**
     * Sorts data in ascending order.
     * @param data The data to be sorted.
     * @param ascending Whether to sort in ascending order.
     * @return The sorted list.
     */
    public ArrayList<Entry<String, Integer>> mergeSortEntries(
            HashMap<String, Integer> data, boolean ascending){
        // initial data
        ArrayList<Entry<String, Integer>> data1 = 
                new ArrayList<Entry<String, Integer>>(data.entrySet());
        // copy of data
        ArrayList<Entry<String, Integer>> data2 = 
                new ArrayList<Entry<String, Integer>>(data1);
        // run inner merge sort function with needed data
        mergeSortEntriesInner(data1, data2, 0, data1.size(), ascending, 1);
        return data2; // return the now sorted data
    }
    
    /**
     * Inner function to keep interface more neat for calling the method.
     * @param dataIn The input data to be merged.
     * @param dataOut The list to output sorted data to.
     * @param start The start index.
     * @param end The end index.
     * @param ascending Whether to sort ascending.
     * @param parity Determines what list to treat as input and what list
     * to treat as output when merging.
     */
    private void mergeSortEntriesInner(
            ArrayList<Entry<String, Integer>> data1, 
            ArrayList<Entry<String, Integer>> data2, 
            int start, int end, boolean ascending, int parity){
        int middle = (start + end) / 2;
        // only do if at-least 2 elements
        if(end - start > 1){
            // split left half recursively
            mergeSortEntriesInner(data1, data2, start, middle,
                    ascending, (parity+1)%2);
            // split right half recursively
            mergeSortEntriesInner(data1, data2, middle, end, 
                    ascending, (parity+1)%2);
        
            // merge halves
            // for parity of 0, take from data 2, put into data 1
            // for parity of 1, take from data 1, put into data 2
            if(parity == 0)
                mergeEntries(data2, data1, start, end, ascending);
            else
                mergeEntries(data1, data2, start, end, ascending);
        }
    }
    
    /**
     * Takes data from dataIn and calls the proper sorting method based 
     * on whether ascending or descending is wanted, and puts the sorted
     * data into dataOut. 
     * @param dataIn The input data to be merged.
     * @param dataOut The list to output sorted data to.
     * @param start The start index.
     * @param end The end index.
     * @param ascending Whether to sort ascending.
     */
    private void mergeEntries(
            ArrayList<Entry<String, Integer>> dataIn, 
            ArrayList<Entry<String, Integer>> dataOut, 
            int start, int end, boolean ascending){
        if(ascending)
            mergeAscending(dataIn, dataOut, start, end);
        else
            mergeDescending(dataIn, dataOut, start, end);
    }
    
    /**
     * Splits the list dataIn in half, then merges the two halves together
     * into dataOut in ascending order.
     * @param dataIn The input data to be merged.
     * @param dataOut The list to output sorted data to.
     * @param start The start index.
     * @param end The end index.
     */
    private void mergeAscending(
            ArrayList<Entry<String, Integer>> dataIn, 
            ArrayList<Entry<String, Integer>> dataOut, 
            int start, int end){
        int middle = (start + end) / 2;
        int indexL = start; // goes until middle
        int indexR = middle; // goes until end
        int indexInsert = start; // the index to insert into in dataOut
        Entry<String, Integer> insertData = null; // data to put in dataOut
        while(indexInsert < end){
            // cur element in left half is smaller
            if(indexR < end && dataIn.get(indexL).getValue().intValue() 
                    < dataIn.get(indexR).getValue().intValue() ){
                // left index still in range
                if(indexL < middle){
                    insertData = dataIn.get(indexL);
                    indexL++;
                }
                else{ // put in element from right
                    insertData = dataIn.get(indexR);
                    indexR++;
                }
                
            }else if(indexR < end){ // element in right is smaller
                // right index still in range
                insertData = dataIn.get(indexR);
                indexR++;
            }
            else{ // r out of bound, definately left
                insertData = dataIn.get(indexL);
                indexL++;
            }
            // put in data and incement index
            dataOut.set(indexInsert++, insertData);
        }
    }
    
    /**
     * Splits the list dataIn in half, then merges the two halves together
     * into dataOut in descending order.
     * @param dataIn The input data to be merged.
     * @param dataOut The list to output sorted data to.
     * @param start The start index.
     * @param end The end index.
     */
    private void mergeDescending(
            ArrayList<Entry<String, Integer>> dataIn, 
            ArrayList<Entry<String, Integer>> dataOut, 
            int start, int end){
        int middle = (start + end) / 2;
        int indexL = start; // goes until middle
        int indexR = middle; // goes until end
        int indexInsert = start; // the index to insert into in dataOut
        Entry<String, Integer> insertData = null; // data to put in dataOut
        while(indexInsert < end){
            // cur element in left half is bigger
            if(indexR < end && dataIn.get(indexL).getValue().intValue()  
                    >= dataIn.get(indexR).getValue().intValue() ){
                // left index still in range
                if(indexL < middle){
                    insertData = dataIn.get(indexL);
                    indexL++;
                }
                else{ // put in element from right
                    insertData = dataIn.get(indexR);
                    indexR++;
                }
                
            }else if(indexR < end){ // right is bigger
                // right index still in range
                insertData = dataIn.get(indexR);
                indexR++;
            }
            else{ // r out of bound, definately left
                insertData = dataIn.get(indexL);
                indexL++;
            }
            // put in data and incement index
            dataOut.set(indexInsert++, insertData);
        }
    }
    
    /**
     * Sorts the <String, Integer> HashMap entries in ascending or 
     * descending order.
     * @param map The HashMap to sort from. Must be <String, Integer>.
     * @param ascending Whether to sort in ascending order.
     * @return An array list of the ordered entries from the HashMap.
     */
    public ArrayList<Entry<String, Integer>> getSortedWordFreq(
            HashMap<String, Integer> map, boolean ascending){
        // to store entries in list form
        ArrayList<Entry<String, Integer>> entriesList = 
                new ArrayList<Entry<String, Integer>>(map.entrySet());

        // sort, keeping key and value matched
        Collections.sort(entriesList, new Comparator<Entry<String, Integer>>(){
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if(ascending)
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
            
        });
        
        return entriesList;
    }

}

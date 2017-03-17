

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
 * - I assume that you only need to sort once to get the 20 most nad least
 * common words.
 * - I assume that I can use java provided sorting algorithms.
 * 
 * Noteworthy Features: 
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
        
        // for some odd reason a single empty string gets through everytime
        textHash.remove("");

        // the sorted word frequencies
        ArrayList<Entry<String, Integer>> sortedWordFreqs = zipf.getSortedWordFreq(textHash);
        
        // print top 20 words
        System.out.println("20 most frequent words: ");
        for(int i = sortedWordFreqs.size()-1; i >= sortedWordFreqs.size() - 20; i--){
            System.out.println(sortedWordFreqs.get(i).getKey() + ": " + 
                    sortedWordFreqs.get(i).getValue().toString());
        }
        
        // print lowest 20 words
        System.out.println("\n20 least frequent words: ");
        for(int i = 0; i < 20; i++){
            System.out.println(sortedWordFreqs.get(i).getKey() + ": " + 
                    sortedWordFreqs.get(i).getValue().toString());
        }
        
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
            nicely: 1
            shooting: 1
            hall: 1
            naturalized: 1
            pretend: 1
            wreck: 1
            morton: 1
            investment: 1
            vegetarianism: 1
            cds: 1
            rounded: 1
            hang: 1
            fontana: 1
            transitional: 1
            dieticians: 1
            resuscitation: 1
            gripping: 1
            q: 1
            readers: 1
            obvious: 1
         * 
         * */
        
    }
    
    /**
     * Sorts the <String, Integer> HashMap entries in ascending order.
     * @param map The HashMap to sort from. Must be <String, Integer>.
     * @return An array list of ascending order of the entries in the HashMap.
     */
    public ArrayList<Entry<String, Integer>> getSortedWordFreq(HashMap<String, Integer> map){
        // to store entries in list form
        ArrayList<Entry<String, Integer>> entriesList = 
                new ArrayList<Entry<String, Integer>>(map.entrySet());

        // sort, keeping key and value matched
        Collections.sort(entriesList, new Comparator<Entry<String, Integer>>(){
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
            
        });
        
        return entriesList;
    }

}

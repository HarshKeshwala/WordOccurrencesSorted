package FileParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class SortedWordOccurrences {

	
	public static void main(String[] args)  throws IOException 
	{
		//Parsing data from the file
		File file = new File("/Users/harshkeshwala/Documents/JavaWorkspace/HarshSanjayCodeTest/src/paragraph.txt"); 
        FileInputStream fileInputStream = new FileInputStream(file); 
       
        Scanner inputFile = new Scanner(fileInputStream);

        //Arraylist to store the words parsed from the paragraph
        ArrayList<String> data = new ArrayList<String>();

        //Adding words to the Arraylist from paragraph file
        while(inputFile.hasNext()) {   	
        	data.add(inputFile.next());
        }
        inputFile.close();
        fileInputStream.close();

        
        //Initialising HashMap
        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();

        //Storing words in to HashMap based on Occurrences from ArrayList
	    for (String s : data) {
	    	s = s.toLowerCase();	//convert each word to lower case
	        if (wordsMap.containsKey(s)) {
	        	wordsMap.put(s, wordsMap.get(s) + 1);
	        } else {
	        	wordsMap.put(s, 1);
	        }
	    }
	    
	    //Printing HashMap entries to the console
	    for (Map.Entry<String, Integer> en : wordsMap.entrySet()) { 
            System.out.println("Key = " + en.getKey() +  
                          ", Value = " + en.getValue()); 
        }
	    
	    System.out.println("************************* SORTED LIST *******************************");
		  
	    //Storing results in to HashMap after being sorted out
	    HashMap<String, Integer> sortedMap = sortWordsByOccurrence(wordsMap);
	    
	    for (Map.Entry<String, Integer> en : sortedMap.entrySet()) { 
            System.out.println("Word = " + en.getKey() +  
                          ", Count = " + en.getValue()); 
        }
	}
	
	// Method to sort words based on occurrence
	public static HashMap<String, Integer> sortWordsByOccurrence(HashMap<String, Integer> map) 
    { 
        // Transfer data to List from HashMap to sort
        List<Map.Entry<String, Integer> > list = 
               new LinkedList<Map.Entry<String, Integer> >(map.entrySet()); 
  
        // Sorting the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >()
        { 
            public int compare(Map.Entry<String, Integer> o1,  
                               Map.Entry<String, Integer> o2) 
            { 
                return (o2.getValue()).compareTo(o1.getValue()); 
            } 
        }); 
          
        // Transfer sorted data back to the HashMap again
        HashMap<String, Integer> resultedMap = new LinkedHashMap<String, Integer>(); 
        for (Map.Entry<String, Integer> obj : list)
        { 
        	resultedMap.put(obj.getKey(), obj.getValue()); 
        } 
        return resultedMap; 
    } 
}
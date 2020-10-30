package FileParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	
	public static void main(String[] args)  throws Exception 
	{
		//Path of the input file
		Path pathInputFile = Paths.get("src/paragraph.txt");
  
		//Path of HTML file to print the result to
		Path pathOutputFile = Paths.get("src/report.html");
        
        //HashMap to store words from the file
        HashMap<String, Integer> wordsMap = getWordsFromFile(pathInputFile);
	  
	    //Storing results in to HashMap after words being sorted out based on occurrence
	    HashMap<String, Integer> sortedMap = sortWordsByOccurrence(wordsMap);
	    
	    //Calling method to write sorted words report to HTML file
	    printReportToHTML(sortedMap, pathOutputFile);
	    
	}
	
	//Method to get words from the input file
	public static HashMap<String, Integer> getWordsFromFile(Path pathInputFile) throws IOException
	{
		
		FileInputStream fileInputStream = new FileInputStream(pathInputFile.toFile()); 
	       
		Scanner inputFile = new Scanner(fileInputStream);
		
		//Arraylist to store the words parsed from the paragraph
        ArrayList<String> data = new ArrayList<String>();

        //Adding words to the Arraylist from paragraph file
        while(inputFile.hasNext()) {
        	data.add(inputFile.next());
        }
       
        inputFile.close();
        fileInputStream.close();

        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
        
        //Storing words in to HashMap based on Occurrences from ArrayList
	    for (String s : data)
	    {
	    	s = s.toLowerCase();	//convert each word to lower case
	        if (wordsMap.containsKey(s))
	        {
	        	wordsMap.put(s, wordsMap.get(s) + 1);
	        } else
	        {
	        	wordsMap.put(s, 1);
	        }
	    }
		
		return wordsMap;
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
	
	// Methods to write result into the HTML file
	public static void printReportToHTML(HashMap<String, Integer> sortedMap, Path pathOutputFile) throws Exception
	{
	    //Writing data in to HTML files
	    try 
	    {	
       	 BufferedWriter bw = new BufferedWriter(new FileWriter(pathOutputFile.toFile()));
       	 bw.write("<div><center><h1>Word count occurrence</h1>");
       	 bw.write("<table border='1'>");
       	 bw.write("<tr><td>Words</td><td>Occurrence</td></tr>");
       
       	 for(Map.Entry<String, Integer> en : sortedMap.entrySet())
       	 {
            bw.write("<tr><td>"+ en.getKey()+ "</td><td>" +en.getValue()+"</tr>");
         }
       	 
       	 bw.write("</table>");
       	 bw.write("</center></div>");
       	 bw.close();
       	 
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }

	}
}
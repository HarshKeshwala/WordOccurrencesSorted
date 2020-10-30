package FileParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class SortedWordOccurrences
{

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
		InputStreamReader input = new InputStreamReader(fileInputStream); 
        BufferedReader reader = new BufferedReader(input);

		String line;

		HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
  
		//Reading words from the paragraph file
        while((line = reader.readLine()) != null)
        {	
        	if(!line.trim().equals(""))
        	{
	        	String[] data = line.split("[ \\n\\t\\r:;.]");
	        	
	        	//Storing words in to HashMap based on Occurrences from ArrayList
	        	for(String s: data)
	        	{
	        		if(s == null || s.trim().equals("")) {
	        			continue; // skip if found empty spaces
	        		}
	        		s = s.toLowerCase();	//convert each word to lower case
	    	        if (wordsMap.containsKey(s))
	    	        {
	    	        	wordsMap.put(s, wordsMap.get(s) + 1);
	    	        } else
	    	        {
	    	        	wordsMap.put(s, 1);
	    	        }
	    	        System.out.println(s);
	        	}
        	}
        }

        input.close();
        fileInputStream.close();
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
		// Apache velocity to embed results in to HTML template
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		
		//HTML template to define format for output
		Template t = ve.getTemplate("src/html_template.vt");
		
		VelocityContext context = new VelocityContext();
		
		//Assigning HashMap to the list to iterate in template
		List<Map.Entry<String, Integer> > list = 
	               new LinkedList<Map.Entry<String, Integer> >(sortedMap.entrySet()); 
		
		
		//Assigning key "map" to the list
		context.put("map",list);
    
		StringWriter writer = new StringWriter();
		
		t.merge(context, writer);
			
		//Writing output with HTML template as an output file
		BufferedWriter bw = new BufferedWriter(new FileWriter(pathOutputFile.toFile()));
	
		bw.write(writer.toString());
	     
	   	bw.close();

	}
}
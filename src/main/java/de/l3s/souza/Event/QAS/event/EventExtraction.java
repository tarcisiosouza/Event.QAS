package de.l3s.souza.Event.QAS.event;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import de.l3s.souza.Event.QAS.DataManager;
import de.l3s.souza.elasticsearch.IndexManager;

public class EventExtraction {

	private int maxEvents;
	private static HashSet<String> stopwords;
	private static IndexManager indexManager;

	public static void main (String args[]) throws IOException
	{
		EventExtraction eventExtraction = new EventExtraction (2);
    	String query = "Give me the stadium of the Stoke City club in the 1900-01 season.";
    	ArrayList<String> events = new ArrayList<String>();
    	
//    	events = eventExtraction.getEventsFromQuery(query);
    	events = eventExtraction.getEntitiesFromQuery(query);
    	
    	for (int i=0;i<events.size();i++)
    		System.out.println(events.get(i));
    	
    	if (events.isEmpty())
    		System.out.println("no events detected");
	}
	
	public int getMaxEvents() {
		return maxEvents;
	}

	public HashSet<String> getStopwords() {
		return stopwords;
	}

	public void setMaxEvents(int maxEvents) {
		this.maxEvents = maxEvents;
	}
	
	public EventExtraction (int maxEvents) throws IOException
	{
		this.maxEvents = maxEvents;
		stopwords = new HashSet<String>();
		
		indexManager = new IndexManager ();
		DataManager dm = new DataManager ();
		
    	File resource = dm.loadStopwordsEnglish().getFile();
    	
    	String stopwords = new String(
    		      Files.readAllBytes(resource.toPath()));
    	
    	StringTokenizer token = new StringTokenizer (stopwords);
    	
    	while (token.hasMoreElements())
    		EventExtraction.stopwords.add(token.nextToken());
	}

	public ArrayList<String> getEventsFromQuery (String query) throws MalformedURLException
	{
		ArrayList<String> events = new ArrayList<String> ();
		Map<Map<String,Object>,Float> results = new HashMap<Map<String,Object>,Float> ();
		
		String keywords = removeStopwords (query);
		
		results = indexManager.getDocumentsSortedByScore(keywords, maxEvents, "uri");

		for (Entry<Map<String, Object>,Float> entry : results.entrySet())
    	{
    		if (entry.getKey().get("uri").toString().contains("http://dbpedia") && entry.getKey().get("type").toString().contentEquals("event"))
    		{
    			
    			String answer = entry.getKey().get("uri").toString();
    			answer = answer.replaceAll("\\<", "");
    			answer = answer.replaceAll("\\>", "");
    			
    			events.add(answer);
    		}
    		
    	}
		
		return events;
		
	}
	
	public ArrayList<String> getEntitiesFromQuery (String query) throws MalformedURLException
	{
		ArrayList<String> events = new ArrayList<String> ();
		Map<Map<String,Object>,Float> results = new HashMap<Map<String,Object>,Float> ();
		
		String keywords = removeStopwords (query);
		
		indexManager.setIndex("dbentityindex");
		indexManager.setType("doc");
		
		results = indexManager.getDocumentsSortedByScore(keywords, maxEvents, "label");

		for (Entry<Map<String, Object>,Float> entry : results.entrySet())
    	{
    		
    			String answer = entry.getKey().get("uri").toString();
    			answer = answer.replaceAll("\\<", "");
    			answer = answer.replaceAll("\\>", "");
    			
    			events.add(answer);
    	}
		
		return events;
		
	}
	
	
	private static boolean isStopWord (String word)
	{
		return stopwords.contains(word.toLowerCase());
	}
	
	private static String removeStopwords (String text)
	{
		String keywords = "";
		
		StringTokenizer token = new StringTokenizer (text);
		
		while (token.hasMoreTokens())
		{
			String current = token.nextToken();
			
			if (!isStopWord(current))
				if (keywords.isEmpty())	
					keywords = keywords + current;
				else
					keywords = keywords + " " + current;
					
		}
		
		return keywords;
		
	}
	
}

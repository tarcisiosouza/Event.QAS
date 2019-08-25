package de.l3s.souza.Event.QAS.knowledgebase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.jena.query.*;

public abstract class KnowledgeBase {
    
	String endpoint = null;
	
	public KnowledgeBase() {}
	
	public KnowledgeBase (String endpoint)
	{
		this.endpoint = endpoint;
	}
	public abstract ResultSet getResult (String query, String kb, String ep);
	
	public abstract boolean getResultBoolean (String query, String kb, String ep);

}

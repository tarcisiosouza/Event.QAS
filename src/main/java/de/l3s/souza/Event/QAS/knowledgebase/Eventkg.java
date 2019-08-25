package de.l3s.souza.Event.QAS.knowledgebase;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;

public class Eventkg extends KnowledgeBase {

	public Eventkg (String endpoint)
	{
		
	}
	
	@Override
	public ResultSet getResult(String qs, String kb, String ep) {
	
	Query query = QueryFactory.create(qs);
	QueryExecution qex = QueryExecutionFactory.sparqlService(ep, query);
	ResultSet rs = qex.execSelect();
	
	return rs;
	}

	@Override
	public boolean getResultBoolean(String qs, String kb, String ep) {

		Query query = QueryFactory.create(qs);
		QueryExecution qex = QueryExecutionFactory.sparqlService(ep, query);
		boolean rs = qex.execAsk();
		return rs;
	}

}

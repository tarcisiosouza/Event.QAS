package de.l3s.souza.Event.QAS;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import de.l3s.souza.Event.word2vec.Word2VecManager;

public class Word2VecTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		Word2VecManager word2Vec = new Word2VecManager ();
		
		word2Vec.loadModel();
		
		Collection<String> c = new ArrayList<String>();
		
		c = word2Vec.getTopSimilarWords("dbo:location", 5);
		
		for (int i=0;i<c.size();i++)
		{
			System.out.print(c.toString());
		}
		
	}

}

package de.l3s.souza.Event.QAS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     * @throws IOException 
     */
    public void testApp() throws IOException
    {
    /*	DataManager dm = new DataManager ();
    	File resource = dm.loadStopwordsEnglish().getFile();
    	
    	String keywords = new String(
    		      Files.readAllBytes(resource.toPath()));
    */	
    }
    
    
    
}

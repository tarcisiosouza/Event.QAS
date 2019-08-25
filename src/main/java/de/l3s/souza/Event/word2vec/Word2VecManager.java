package de.l3s.souza.Event.word2vec;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class Word2VecManager {

	private SentenceIterator iter;
	private Word2Vec word2Vec;
	private String path;
	
	public Word2VecManager () throws FileNotFoundException, IOException
	{
		
		try (InputStream input = new FileInputStream ("/Users/tarcisio/workspace-oxygen/Event.QAS/src/main/resources/application.properties"))
		{
			Properties p =  new Properties ();
			
			p.load(input);
			path = p.getProperty("word2vec.model");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void train (String path, String outputModelName)
    {
    	 System.out.println ("Training...");
    	 iter = new LineSentenceIterator(new File(path));
    	
    	 TokenizerFactory t = new DefaultTokenizerFactory();
         t.setTokenPreProcessor(new CommonPreprocessor());
         InMemoryLookupCache cache = new InMemoryLookupCache();
         WeightLookupTable<VocabWord> table = new InMemoryLookupTable.Builder<VocabWord>()
                 .vectorLength(100)
                 .useAdaGrad(false)
                 .cache(cache)
                 .lr(0.025f).build();

         word2Vec = new Word2Vec.Builder()
                 .minWordFrequency(1)
                 .iterations(5)
                 .epochs(10)
                 .layerSize(300)
                 .seed(42)
                 .windowSize(5)
                 .iterate(iter)
                 .tokenizerFactory(t)
                 .lookupTable(table)
                 .vocabCache(cache)
                 .build();

         word2Vec.fit();
         WordVectorSerializer.writeFullModel(word2Vec, outputModelName);
         System.out.println ("Finishing Training...");
    }
	
	public void loadModel ()
	{
		word2Vec = WordVectorSerializer.loadFullModel(path);
	}
	
	public Collection<String> getTopSimilarWords (String word, int limit)
	{
		Collection<String> words = new ArrayList<String>();
		
		words = word2Vec.wordsNearest(word, limit);
		
		return words;
		
	}
	
	public static void main (String args[]) throws FileNotFoundException, IOException
	{
		Word2VecManager w = new Word2VecManager ();
		
		//w.train(args[0], args[1]);
		//w.loadModel();
	}
}

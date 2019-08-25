package de.l3s.souza.Event.QAS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class DataManager {

	@Value( "${keywordsEnglish.file}" )
	private String keywords;
	
	public Resource loadStopwordsEnglish() {
	    return new ClassPathResource("stopwords_en.txt");
	}	
}
	
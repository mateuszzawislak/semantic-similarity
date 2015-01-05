package pl.edu.pw.elka.mzawisl2.semsim.analaysis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService.Param;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordLemmatizer implements Analyzer {

	protected StanfordCoreNLP pipeline;

	private Set<String> pos;

	private Set<String> stopWords;

	private final static ConfigService configService = ConfigService.getInstance();

	public StanfordLemmatizer() {
		// Create StanfordCoreNLP object properties, with POS tagging
		// (required for lemmatization), and lemmatization
		Properties props;
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");

		// StanfordCoreNLP loads a lot of models, so you probably
		// only want to do this once per execution
		this.pipeline = new StanfordCoreNLP(props);

		this.pos = new HashSet<String>(configService.getList(Param.POS_ACCEPTED));
		this.stopWords = new HashSet<String>(configService.getList(Param.STOP_WORDS));
	}

	public List<String> lemmatize(String documentText) {
		List<String> lemmas = new LinkedList<String>();

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(documentText);

		// run all Annotators on this text
		this.pipeline.annotate(document);

		// Iterate over all of the sentences found
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// Iterate over all tokens in a sentence
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// Retrieve and add the lemma for each word into the list of
				// lemmas
				if (this.pos.contains(token.get(PartOfSpeechAnnotation.class))) {
					String word = token.get(LemmaAnnotation.class);
					if (!this.stopWords.contains(word))
						lemmas.add(token.get(LemmaAnnotation.class));
				}
			}
		}

		return lemmas;
	}
}

package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.analaysis.Analyzer;
import pl.edu.pw.elka.mzawisl2.semsim.analaysis.StanfordLemmatizer;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService.Param;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.model.Pair;
import pl.edu.pw.elka.mzawisl2.semsim.model.ScoredPair;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.TextUtils;

public class TextsComparator implements TextsComparatorLocal {

	private static Logger log = Logger.getLogger(TextsComparator.class);

	private final static ConfigService configService = ConfigService.getInstance();

	private boolean printDebug;

	private WordToWordComparator wtwComparator = null;

	private Analyzer analyzer = null;

	private float similarityThreshold;

	public TextsComparator(boolean printDebug) throws SemSimException {
		this.printDebug = printDebug;

		init();
	}

	@Override
	public float compare(String text1, String text2) {
		try {
			List<String> tokens1 = analyzer.lemmatize(text1);
			List<String> tokens2 = analyzer.lemmatize(text2);

			if (printDebug) {
				System.out.println("Analyzed first text:\n" + tokens1);
				System.out.println("Analyzed second text:\n" + tokens2);
			}

			List<ScoredPair> pairsA = constructPairs(tokens1, tokens2);

			float simAB = getTokensSimilarity(pairsA);
			float normA = getTextNorm(pairsA);
			if (printDebug) {
				System.out.println("Sim(A -> B) = " + simAB);
				System.out.println("normA = " + normA);
			}

			List<ScoredPair> pairsB = constructPairs(tokens2, tokens1);

			float simBA = getTokensSimilarity(pairsB);
			float normB = getTextNorm(pairsB);
			if (printDebug) {
				System.out.println("Sim(B -> A) = " + simBA);
				System.out.println("normB = " + normB);
			}

			return (simAB + simBA) / (2 * Math.max(normB, normA));
		} catch (SemSimException e) {
			log.error(LogUtils.getDescr(e));
			return 0;
		}
	}

	private List<ScoredPair> constructPairs(List<String> firstTextTokens, List<String> secondTextTokens) {
		List<String> tokens1 = new ArrayList<String>(firstTextTokens);
		List<String> tokens2 = new ArrayList<String>(secondTextTokens);

		try {
			List<ScoredPair> possiblePairs = new ArrayList<ScoredPair>();
			for (String word1 : tokens1) {
				for (String word2 : tokens2) {
					float similarity = wtwComparator.getSimilarity(word1, word2);

					if (similarity > similarityThreshold)
						possiblePairs.add(new ScoredPair(similarity, new Pair(word1, word2)));
				}
			}

			Collections.sort(possiblePairs, new Comparator<ScoredPair>() {

				@Override
				public int compare(ScoredPair o1, ScoredPair o2) {
					float s1 = o1.getScore();
					float s2 = o2.getScore();

					if (s1 == s2)
						return 0;

					return s1 > s2 ? -1 : 1;
				}
			});

			List<ScoredPair> results = new ArrayList<ScoredPair>();
			for (ScoredPair scoredPair : possiblePairs) {
				String firstWord = scoredPair.getWords().getWord1();
				String secondWord = scoredPair.getWords().getWord2();

				if (tokens1.contains(firstWord) && tokens2.contains(secondWord)) {
					tokens1.remove(firstWord);
					tokens2.remove(secondWord);

					results.add(scoredPair);
				}

				if (tokens1.isEmpty() || tokens2.isEmpty())
					break;
			}

			if (printDebug)
				System.out.println("\nConstructed set (S) of exclusive pairs of similar words between the two input texts:\n" + results);

			return results;
		} catch (SemSimException e) {
			log.error(LogUtils.getDescr(e));
			return null;
		}
	}

	private float getTextNorm(List<ScoredPair> pairs) throws SemSimException {
		float result = 0.0f;

		for (ScoredPair scoredPair : pairs) {
			result += wtwComparator.getIdf(scoredPair.getWords().getWord1());
		}

		return result;
	}

	private float getTokensSimilarity(List<ScoredPair> pairs) throws SemSimException {
		float similarity = 0.0f;
		for (ScoredPair scoredPair : pairs) {
			Pair words = scoredPair.getWords();
			similarity += scoredPair.getScore() * Math.max(wtwComparator.getIdf(words.getWord1()), wtwComparator.getIdf(words.getWord2()));
		}

		return similarity;
	}

	private void init() throws SemSimException {
		try {
			String discoDir = configService.getParam(Param.DISCO_INDEX_DIR);
			long maxFreq = configService.getParamLong(Param.MAX_FREQUENT, Long.MAX_VALUE);

			if (!TextUtils.isSet(discoDir))
				throw new SemSimException("Disco dictionary param (" + Param.DISCO_INDEX_DIR + ") not found");

			wtwComparator = new DISCOComparator(discoDir, maxFreq);
			analyzer = new StanfordLemmatizer();

			similarityThreshold = configService.getParamFloat(Param.SIMILARITY_THRESHOLD, 0.0f);
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			throw new SemSimException(e);
		}
	}
}

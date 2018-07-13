package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.linguatools.disco.CorruptConfigFileException;
import de.linguatools.disco.DISCO;
import de.linguatools.disco.DISCO.SimilarityMeasure;
import de.linguatools.disco.WrongWordspaceTypeException;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;

public class DISCOComparator implements WordToWordComparator {

	private static Logger log = LoggerFactory.getLogger(DISCOComparator.class);

	private DISCO disco;

	private long maxFreq;

	public DISCOComparator(String dir, long maxFreq) throws IOException, CorruptConfigFileException {
		disco = DISCO.load(dir);
		this.maxFreq = maxFreq;
	}

	@Override
	public int getFrequency(String word) throws IOException {
		return disco.frequency(word);
	}

	@Override
	public double getIdf(String word) throws SemSimException {
		int freq;
		try {
			freq = disco.frequency(word);
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			throw new SemSimException(e);
		}

		return 0 == freq ? 0 : Math.log((maxFreq + 1) / freq);
	}

	@Override
	public float getSimilarity(String word1, String word2) throws IOException, WrongWordspaceTypeException {
		return disco.secondOrderSimilarity(word1, word2, DISCO.getVectorSimilarity(SimilarityMeasure.KOLB));
	}

}

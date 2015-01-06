package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import de.linguatools.disco.DISCO;

public class DISCOComparator implements WordToWordComparator {

	private static Logger log = Logger.getLogger(DISCOComparator.class);

	private DISCO disco;

	private long maxFreq;

	public DISCOComparator(String dir, long maxFreq) throws IOException {
		disco = new DISCO(dir, false);
		this.maxFreq = maxFreq;
	}

	@Override
	public int getFrequency(String word) {
		try {
			return disco.frequency(word);
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			return 0;
		}
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
	public float getSimilarity(String word1, String word2) throws SemSimException {
		try {
			return disco.secondOrderSimilarity(word1, word2);
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			throw new SemSimException(e);
		}
	}

}

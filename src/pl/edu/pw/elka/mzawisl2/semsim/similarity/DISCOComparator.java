package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import de.linguatools.disco.DISCO;

public class DISCOComparator implements WordToWordComparator {

	private static Logger log = Logger.getLogger(DISCOComparator.class);

	private String dir;

	private DISCO disco;

	public DISCOComparator(String dir) throws IOException {
		disco = new DISCO(dir, false);
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

	@Override
	public double getIdf(String word) throws SemSimException {
		int freq;
		try {
			freq = disco.frequency(word);
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			throw new SemSimException(e);
		}

		// TODO
		return Math.log(1 / freq);
	}

}

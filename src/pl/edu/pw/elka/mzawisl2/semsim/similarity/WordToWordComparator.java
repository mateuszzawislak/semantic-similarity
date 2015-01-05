package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;

public interface WordToWordComparator {

	public float getSimilarity(String word1, String word2) throws SemSimException;

	public double getIdf(String word) throws SemSimException;

}

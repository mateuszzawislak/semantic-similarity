package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;

import de.linguatools.disco.WrongWordspaceTypeException;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;

public interface WordToWordComparator {

	public float getSimilarity(String word1, String word2) throws IOException, WrongWordspaceTypeException;

	public double getIdf(String word) throws SemSimException;

	public int getFrequency(String word) throws IOException;

}

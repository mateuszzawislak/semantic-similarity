package pl.edu.pw.elka.mzawisl2.semsim.model;

public class Pair {

	private String word1;

	private String word2;

	public String getWord1() {
		return word1;
	}

	public String getWord2() {
		return word2;
	}

	public Pair(String word1, String word2) {
		this.word1 = word1;
		this.word2 = word2;
	}

	@Override
	public String toString() {
		return "Pair [word1=" + word1 + ", word2=" + word2 + "]";
	}

}

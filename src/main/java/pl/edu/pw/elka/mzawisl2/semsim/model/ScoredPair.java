package pl.edu.pw.elka.mzawisl2.semsim.model;

public class ScoredPair {

	private float score;

	private Pair words;

	public ScoredPair(float score, Pair words) {
		this.score = score;
		this.words = words;
	}

	@Override
	public String toString() {
		return "ScoredPair [score=" + score + ", words=" + words + "]";
	}

	public float getScore() {
		return score;
	}

	public Pair getWords() {
		return words;
	}

}

package pl.edu.pw.elka.mzawisl2.semsim;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.evaluation.Evaluator;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparator;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparatorLocal;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);

	private static final String QUIET = "-quiet";

	public static void main(String[] args) {
		boolean printDebug = true;

		for (int i = 0; i < args.length; ++i) {
			String arg = args[i];
			switch (arg) {
				case QUIET: {
					printDebug = false;
				}
					break;
			}
		}

		try {
			TextsComparatorLocal textsComparator = new TextsComparator(printDebug);
			Evaluator evaluator = new Evaluator();

			float similarity = textsComparator.compare("The procedure is generally performed in the second or third trimester.",
					"The technique is used during the second and, occasionally, third trimester of pregnancy");

			int rate = evaluator.discretizeRate(similarity);

			System.out.println("\nTexts semantic similarity: " + similarity + " (rate: " + rate + ")");

		} catch (SemSimException e) {
			log.error("Something went wrong: " + LogUtils.getDescr(e));
		}

		System.exit(0);
	}
}

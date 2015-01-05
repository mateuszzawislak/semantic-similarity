package pl.edu.pw.elka.mzawisl2.semsim;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparator;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparatorLocal;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		TextsComparatorLocal textsComparator;
		try {
			textsComparator = new TextsComparator();

			System.out.println("\nTexts semantic similarity: "
					+ textsComparator.compare("The procedure is generally performed in the second or third trimester.",
							"The technique is used during the second and, occasionally, third trimester of pregnancy"));
		} catch (SemSimException e) {
			log.error("Something went wrong: " + LogUtils.getDescr(e));
		}

		System.exit(0);
	}
}

package pl.edu.pw.elka.mzawisl2.semsim;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.elka.mzawisl2.semsim.evaluation.Evaluator;
import pl.edu.pw.elka.mzawisl2.semsim.exception.InvalidUseException;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparator;
import pl.edu.pw.elka.mzawisl2.semsim.similarity.TextsComparatorLocal;
import pl.edu.pw.elka.mzawisl2.semsim.util.FileUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.TextUtils;

public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);

	private static final String QUIET = "-quiet";

	private static final String FIRST_DOC = "-f";

	private static final String SECOND_DOC = "-s";

	private static String getArg(String[] args, int index) throws InvalidUseException {
		if (index >= args.length) {
			throw new InvalidUseException("Missing argument!");
		}

		return args[index];
	}

	private static String getProperUse() {
		String properUse = "java -Dpl.edu.pw.elka.mzawisl2.semsim.config=/path/to/semsim.properties -jar semsim.jar -f /path/to/first/document -s /path/to/second/document [-quiet]:";
		String example = "java -Dpl.edu.pw.elka.mzawisl2.semsim.config=/home/mza/storage/workspaces/workspace-mars-tvn/semantic-similarity/etc/semsim.properties -jar build/libs/semantic-similarity-0.1.jar -f /home/mza/Downloads/doc1.txt -s /home/mza/Downloads/doc2.txt";

		return properUse + "\n\nExample use: " + example;
	}

	public static void main(String[] args) {
		try {
			boolean printDebug = true;
			String firstDoc = null, secondDoc = null;

			for (int i = 0; i < args.length; ++i) {
				String arg = args[i];
				switch (arg) {
					case QUIET: {
						printDebug = false;
					}
						break;
					case FIRST_DOC: {
						firstDoc = getArg(args, ++i);
					}
						break;
					case SECOND_DOC: {
						secondDoc = getArg(args, ++i);
					}
						break;
				}
			}

			if (!TextUtils.isSet(firstDoc) || !TextUtils.isSet(secondDoc))
				throw new InvalidUseException("Two documents paths must be given!");

			TextsComparatorLocal textsComparator = new TextsComparator(printDebug);
			Evaluator evaluator = new Evaluator();

			float similarity = textsComparator.compare(FileUtils.readFile(firstDoc), FileUtils.readFile(secondDoc));
			int rate = evaluator.discretizeRate(similarity);

			System.out.println("\nTexts semantic similarity: " + similarity + " (rate: " + rate + ")");
		} catch (SemSimException e) {
			log.error("Something went wrong: " + LogUtils.getDescr(e));
		} catch (IOException e) {
			log.error("The problem with the files occured: " + LogUtils.getDescr(e));
		} catch (InvalidUseException e) {
			System.out.println("Invalid use: " + e.getMessage() + "\n\nValid use: " + getProperUse());
		}

		System.exit(0);
	}
}

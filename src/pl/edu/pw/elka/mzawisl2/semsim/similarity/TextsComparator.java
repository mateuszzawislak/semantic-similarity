package pl.edu.pw.elka.mzawisl2.semsim.similarity;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.analaysis.Analyzer;
import pl.edu.pw.elka.mzawisl2.semsim.analaysis.StanfordLemmatizer;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService.Param;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.TextUtils;

public class TextsComparator implements TextsComparatorLocal {

	private static Logger log = Logger.getLogger(TextsComparator.class);

	private final static ConfigService configService = ConfigService.getInstance();

	private WordToWordComparator wtwComparator = null;

	private Analyzer analyzer = null;

	public TextsComparator() throws SemSimException {
		init();
	}

	private void init() throws SemSimException {
		try {
			String discoDir = configService.getParam(Param.DISCO_INDEX_DIR);

			if (!TextUtils.isSet(discoDir))
				throw new SemSimException("Disco dictionary param (" + Param.DISCO_INDEX_DIR + ") not found");

			wtwComparator = new DISCOComparator(discoDir);

			analyzer = new StanfordLemmatizer();
		} catch (IOException e) {
			log.error(LogUtils.getDescr(e));
			throw new SemSimException(e);
		}
	}

	@Override
	public float compare(String text1, String text2) {
		List<String> tokens1 = analyzer.lemmatize(text1);
		System.out.println(tokens1);
		List<String> tokens2 = analyzer.lemmatize(text2);
		System.out.println(tokens2);
		// TODO Auto-generated method stub
		return 0;
	}

}

package pl.edu.pw.elka.mzawisl2.semsim.evaluation;

import java.util.List;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService.Param;
import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.BaseUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;

public class Evaluator {

	private static Logger log = Logger.getLogger(Evaluator.class);

	private final static ConfigService configService = ConfigService.getInstance();

	private List<Float> rateDiscretization;

	public Evaluator() {
		try {
			rateDiscretization = configService.getFloatList(Param.RATE_DISCRETIZATION);
		} catch (SemSimException e) {
			log.error("Discretization rates configuration not found (param " + Param.RATE_DISCRETIZATION + "): " + LogUtils.getDescr(e));
		}
	}

	public int discretizeRate(float evaluation) {
		if (BaseUtils.isNullOrEmpty(rateDiscretization))
			return -1;

		for (int i = 0; i < rateDiscretization.size(); ++i) {
			if (evaluation < rateDiscretization.get(i))
				return i;
		}

		return rateDiscretization.size();
	}
}

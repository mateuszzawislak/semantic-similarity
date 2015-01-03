package pl.edu.pw.elka.mzawisl2.semsim;

import org.apache.log4j.Logger;

import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService;
import pl.edu.pw.elka.mzawisl2.semsim.config.ConfigService.Param;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);

	private final static ConfigService configService = ConfigService.getInstance();

	public static void main(String[] args) {
		// TODO
		System.out.println("Test");

		System.out.println(configService.getParam(Param.TEST_PARAM));
		log.info(configService.getParam(Param.TEST_PARAM));

		System.exit(0);
	}

}

package pl.edu.pw.elka.mzawisl2.semsim.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.pw.elka.mzawisl2.semsim.exception.SemSimException;
import pl.edu.pw.elka.mzawisl2.semsim.util.LogUtils;
import pl.edu.pw.elka.mzawisl2.semsim.util.TextUtils;

public class ConfigService {

	public enum Param {
		DISCO_INDEX_DIR("disco.index.dir"),
		POS_ACCEPTED("pos.accepted"),
		STOP_WORDS("stop.words"),
		SIMILARITY_THRESHOLD("similarity.threshold"),
		MAX_FREQUENT("max.frequent"),
		RATE_DISCRETIZATION("rate.discretization");

		private String name;

		Param(String name) {
			this.name = name;
		}

		public String getName() {
			return name;

		}
	}

	private static Logger log = LoggerFactory.getLogger(ConfigService.class);

	public static ConfigService getInstance() {
		if (instance == null) {
			synchronized (ConfigService.class) {
				if (instance == null) {
					try {
						instance = new ConfigService();
					} catch (SemSimException e) {
						log.error("Failed to init ConfigService");
						return null;
					}
				}
			}
		}

		return instance;
	}

	private String configFile = System.getProperty("pl.edu.pw.elka.mzawisl2.semsim.config", "semsim.properties");

	private final static String LIST_SEPARATOR = ",";

	private Properties props;

	private static volatile ConfigService instance = null;

	private ConfigService() throws SemSimException {
		init();
	}

	public List<Float> getFloatList(Param param) throws SemSimException {
		String types = getParam(param);
		List<Float> results = new ArrayList<>();

		if (!TextUtils.isSet(types))
			return results;

		String[] typeList = types.split(LIST_SEPARATOR);

		for (int i = 0; i < typeList.length; i++) {
			String name = typeList[i].trim();

			try {
				Float value = Float.parseFloat(name);
				results.add(value);
			} catch (NumberFormatException e) {
				log.error("Invalid field type. Expected Float!" + LogUtils.getDescr(e));
				throw new SemSimException("Invalid field type. Expected Float!");
			}
		}

		return results;
	}

	public List<Integer> getIntegerList(Param param) throws SemSimException {
		String types = getParam(param);
		List<Integer> results = new ArrayList<>();

		if (!TextUtils.isSet(types))
			return results;

		String[] typeList = types.split(LIST_SEPARATOR);

		for (int i = 0; i < typeList.length; i++) {
			String name = typeList[i].trim();

			try {
				Integer value = Integer.parseInt(name);
				results.add(value);
			} catch (NumberFormatException e) {
				log.error("Invalid field type. Expected Integer!" + LogUtils.getDescr(e));
				throw new SemSimException("Invalid field type. Expected Integer!");
			}
		}

		return results;
	}

	public List<String> getList(Param param) {
		String types = getParam(param);
		List<String> results = new ArrayList<>();

		if (!TextUtils.isSet(types))
			return results;

		String[] typeList = types.split(LIST_SEPARATOR);

		for (int i = 0; i < typeList.length; i++) {
			results.add(typeList[i].trim());
		}

		return results;
	}

	public String getParam(Param param) {
		return getParam(param, null);
	}

	public String getParam(Param param, String defaultValue) {
		if (null == param)
			return null;

		String _val = props.getProperty(param.getName());

		return _val != null ? _val.trim() : defaultValue;
	}

	public Boolean getParamBool(Param param) {
		return getParamBool(param, null);
	}

	public Boolean getParamBool(Param param, Boolean defaultValue) {
		String val = getParam(param);

		if (null == val)
			return defaultValue;

		return TextUtils.toBool(val);
	}

	public Float getParamFloat(Param param) {
		return getParamFloat(param, null);
	}

	public Float getParamFloat(Param param, Float defaultValue) {
		if (null == param)
			return null;

		Float _val = null;
		try {
			_val = Float.parseFloat(getParam(param));
		} catch (NumberFormatException e) {
			// ignore
		} catch (NullPointerException e) {
			// ignore
		}

		return _val != null ? _val : defaultValue;
	}

	public Integer getParamInt(Param param) {
		return getParamInt(param, null);
	}

	public Integer getParamInt(Param param, Integer defaultValue) {
		if (null == param)
			return null;

		Integer _val = null;
		try {
			_val = Integer.parseInt(getParam(param));
		} catch (NumberFormatException e) {
			// ignore
		} catch (NullPointerException e) {
			// ignore
		}

		return _val != null ? _val : defaultValue;
	}

	public Long getParamLong(Param param) {
		return getParamLong(param, null);
	}

	public Long getParamLong(Param param, Long defaultValue) {
		String val = getParam(param);

		if (null == val)
			return defaultValue;

		return TextUtils.toLong(val);
	}

	private void init() throws SemSimException {
		load();
	}

	private void load() throws SemSimException {
		Properties _props = new Properties();

		if (null == configFile)
			return;

		configFile = configFile.trim();

		if (!TextUtils.isSet(configFile))
			return;

		InputStream is = null;

		try {
			is = new FileInputStream(configFile);
			_props.load(is);
		} catch (IOException e) {
			log.error("Failed to load configuration from " + new File(configFile).getAbsolutePath() + "\n" + LogUtils.getDescr(e));
			throw new SemSimException("Failed to load configuration from " + new File(configFile).getAbsolutePath());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

		// activate config
		props = _props;
	}
}

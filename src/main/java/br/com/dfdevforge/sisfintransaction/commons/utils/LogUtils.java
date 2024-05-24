package br.com.dfdevforge.sisfintransaction.commons.utils;

import java.lang.invoke.MethodHandles;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
	private static final String LOGGER_PATTERN = "Sisfin Transaction Error: {}";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected  LogUtils() {}

	public void error(Object message) {
		LOGGER.error(LOGGER_PATTERN, message);
	}

	public void info(Object message) {
		LOGGER.info(LOGGER_PATTERN, message);
	}

	public void warn(Object message) {
		LOGGER.warn(LOGGER_PATTERN, message);
	}

	public String stackTrace(Exception exception) {
		final String TAB = "\t";
		final String BREAK = "\n";
		Date now = new Date();
		StringBuilder stackTrace = new StringBuilder();

		stackTrace.append(TAB + "Exception: " + exception + BREAK);

		for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
			if (StringUtils.contains(stackTraceElement.getClassName(), "dfdevforge")) {
				stackTrace.append(TAB + stackTraceElement + BREAK);
			}
		}

		this.error("Inicio log " + now.getTime() + BREAK + BREAK + stackTrace.toString());
		this.error("Fim log " + now.getTime() + BREAK);

		return stackTrace.toString();
	}
}
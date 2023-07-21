package br.com.dfdevforge.common.util;

import java.lang.invoke.MethodHandles;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dfdevforge.sisfintransaction.entities.UserEntity;

public class LogUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	protected  LogUtils() {}

	public void error(Object message) {
		LOGGER.error(message == null ? "null" : message.toString());
	}

	public void info(Object message) {
		LOGGER.info(message == null ? "null" : message.toString());
	}

	public void warn(Object message) {
		LOGGER.warn(message == null ? "null" : message.toString());
	}

	public void stackTrace(Exception exception, UserEntity user) {
		final String TAB = "\t";
		final String BREAK = "\n";
		Date now = new Date();
		StringBuilder stackTrace = new StringBuilder();

		String message = "User not found";
		if (user != null && user.getName() != null)
			message = user.getName();

		stackTrace.append(TAB + "Exception: " + exception + BREAK);
		stackTrace.append(TAB + "User: " + message + BREAK + BREAK);
		
		for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
			if (StringUtils.contains(stackTraceElement.getClassName(), "dfdevforge")) {
				stackTrace.append(TAB + stackTraceElement + BREAK);
			}
		}

		error("Inicio log " + now.getTime() + BREAK + BREAK + stackTrace.toString());
		error("Fim log " + now.getTime() + BREAK);
	}
}
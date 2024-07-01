package br.com.dfdevforge.sisfintransaction.commons.persistences;

import java.util.Calendar;
import java.util.Date;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;

public abstract class BasePersistence {
	protected Date findStartDate(Date periodDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getFirstDayOfMonth(periodDate));
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}

	protected Date findEndDate(Date periodDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getLastDayOfMonth(periodDate));
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}
}
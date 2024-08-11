package br.com.dfdevforge.sisfintransaction.statement.entities;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlStatementItemEntity {
	private String description;
	private String date;
	private String value;

	public BigDecimal getValueInBigDecimal() {
		return this.stringToBigDecimal(value);
	}

	public Date getDateObject() {
		try {
			return Utils.date.toDateFromString(date);
		}
		catch (ParseException e) {
			Utils.log.stackTrace(e);
			return null;
		}
	}

	public BigDecimal stringToBigDecimal(String value) {
		value = value.replace(".", "");
		value = value.replace(",", ".");

		return new BigDecimal(value);
	}
}
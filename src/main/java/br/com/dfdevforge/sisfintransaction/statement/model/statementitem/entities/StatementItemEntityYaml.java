package br.com.dfdevforge.sisfintransaction.statement.model.statementitem.entities;

import java.math.BigDecimal;
import java.util.Date;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementItemEntityYaml {
	private String description;
	private String date;
	private String value;

	public BigDecimal getValueInBigDecimal() {
		return this.stringToBigDecimal(value);
	}

	public Date getDateObject() {
		return Utils.date.toDateFromString(date);
	}

	public BigDecimal stringToBigDecimal(String value) {
		value = value.replace(".", "");
		value = value.replace(",", ".");

		return new BigDecimal(value);
	}
}
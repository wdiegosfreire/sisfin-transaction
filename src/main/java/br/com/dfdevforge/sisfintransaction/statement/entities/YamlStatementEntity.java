package br.com.dfdevforge.sisfintransaction.statement.entities;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlStatementEntity {
	private Integer month;
	private Integer year;
	private String openingBalance;
	private String closingBalance;
	private List<YamlStatementItemEntity> itemList;

	public BigDecimal getOpeningBalanceInBigDecimal() {
		return this.stringToBigDecimal(openingBalance);
	}

	public BigDecimal getClosingBalanceInBigDecimal() {
		return this.stringToBigDecimal(closingBalance);
	}

	public BigDecimal stringToBigDecimal(String value) {
		value = value.replace(".", "");
		value = value.replace(",", ".");

		return new BigDecimal(value);
	}
}
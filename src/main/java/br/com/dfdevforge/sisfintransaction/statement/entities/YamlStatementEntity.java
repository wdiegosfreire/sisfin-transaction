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
	private String dueDate;
	private String paymentDate;
	private String totalValue;
	private String openingBalance;
	private String closingBalance;
	private List<YamlStatementItemEntity> itemList;

	public BigDecimal getTotalValueInBigDecimal() {
		return this.stringToBigDecimal(this.totalValue);
	}

	public BigDecimal getOpeningBalanceInBigDecimal() {
		return this.stringToBigDecimal(this.openingBalance);
	}

	public BigDecimal getClosingBalanceInBigDecimal() {
		return this.stringToBigDecimal(this.closingBalance);
	}

	public BigDecimal stringToBigDecimal(String value) {
		value = value.replace(".", "");
		value = value.replace(",", ".");

		return new BigDecimal(value);
	}
}
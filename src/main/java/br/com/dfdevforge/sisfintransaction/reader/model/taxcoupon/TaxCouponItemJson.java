package br.com.dfdevforge.sisfintransaction.reader.model.taxcoupon;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxCouponItemJson {
	private Integer item;
	private String code;
	private String codeTraffic;
	private String description;
	private BigDecimal amount;
	private BigDecimal price;
	private String un;
	private TaxCouponItemRegisterJson register;

	@Getter
	@Setter
	public class TaxCouponItemRegisterJson {
		private BigDecimal addition;
		private BigDecimal additionApportionment;
		private BigDecimal discount;
		private BigDecimal discountApportionment;
	}
}
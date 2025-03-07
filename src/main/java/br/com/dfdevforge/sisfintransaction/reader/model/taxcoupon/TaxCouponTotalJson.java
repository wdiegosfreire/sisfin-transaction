package br.com.dfdevforge.sisfintransaction.reader.model.taxcoupon;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxCouponTotalJson {
	private BigDecimal gross;
	private BigDecimal totalDiscountAddition;
	private BigDecimal subtotalDiscount;
	private BigDecimal subtotalAddition;
	private BigDecimal total;
}
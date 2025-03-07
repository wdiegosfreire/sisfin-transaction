package br.com.dfdevforge.sisfintransaction.reader.model.taxcoupon;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxCouponPaymentJson {
	private String method;
	private BigDecimal value;
}
package br.com.dfdevforge.sisfintransaction.reader.model.taxcoupon;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxCouponJson {
	private String taxIdNumber;
	private String extractNumber;
	private String companyName;
	private String fantasyName;
	private Date emissionDate;
	private String barcode;
	private String qrcode;
	private String satNumber;
	private TaxCouponAddressJson address;
	private TaxCouponTotalJson total;
	private List<TaxCouponItemJson> items;
	private List<TaxCouponPaymentJson> payments;
}
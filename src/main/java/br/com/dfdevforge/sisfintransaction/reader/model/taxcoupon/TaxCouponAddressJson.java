package br.com.dfdevforge.sisfintransaction.reader.model.taxcoupon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxCouponAddressJson {
	private String street;
	private String number;
	private String neighborhood;
	private String zipCode;
	private TaxCouponAddressCityJson city;

	@Getter
	@Setter
	public class TaxCouponAddressCityJson {
		private String name;
	}
}
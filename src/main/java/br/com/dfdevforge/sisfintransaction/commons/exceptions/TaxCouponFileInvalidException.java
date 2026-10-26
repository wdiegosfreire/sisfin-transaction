package br.com.dfdevforge.sisfintransaction.commons.exceptions;

public class TaxCouponFileInvalidException extends BaseException implements HttpStatusBadRequest {
	private static final long serialVersionUID = 1L;

	public TaxCouponFileInvalidException() {
		super("Invalid Tax Coupon File", "Unable to read the tax coupon file. Please, check if it is a valid .json file.");
	}
}

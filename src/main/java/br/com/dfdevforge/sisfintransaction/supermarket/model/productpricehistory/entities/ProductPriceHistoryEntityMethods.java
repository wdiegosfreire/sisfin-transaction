package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductPriceHistoryEntityMethods {
	private final ProductPriceHistoryEntity productPriceHistory;

	public ProductPriceHistoryEntityMethods() {
		this.productPriceHistory = null;
	}
}
package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductInfoEntityMethods {
	private final ProductInfoEntity productInfo;

	public ProductInfoEntityMethods() {
		this.productInfo = null;
	}
}
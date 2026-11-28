package br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductEntityMethods {
	private final ProductEntity product;

	public ProductEntityMethods() {
		this.product = null;
	}
}
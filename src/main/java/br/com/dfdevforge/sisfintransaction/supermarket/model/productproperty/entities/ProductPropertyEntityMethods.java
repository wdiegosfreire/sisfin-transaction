package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductPropertyEntityMethods {
	private final ProductPropertyEntity productProperty;

	public ProductPropertyEntityMethods() {
		this.productProperty = null;
	}
}
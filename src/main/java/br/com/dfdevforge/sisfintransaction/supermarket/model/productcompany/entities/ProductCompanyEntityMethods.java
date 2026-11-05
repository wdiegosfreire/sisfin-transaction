package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductCompanyEntityMethods {
	private final ProductCompanyEntity productCompany;

	public ProductCompanyEntityMethods() {
		this.productCompany = null;
	}
}
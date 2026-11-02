package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductCategoryEntityMethods {
	private final ProductCategoryEntity productCategory;

	public ProductCategoryEntityMethods() {
		this.productCategory = null;
	}
}
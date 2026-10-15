package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BrandEntityMethods {
	private final BrandEntity brand;

	public BrandEntityMethods() {
		this.brand = null;
	}

	public boolean hasEmail() {
		return StringUtils.isNotBlank(this.brand.getEmail());
	}

	public boolean hasWebsite() {
		return StringUtils.isNotBlank(this.brand.getWebsite());
	}
}
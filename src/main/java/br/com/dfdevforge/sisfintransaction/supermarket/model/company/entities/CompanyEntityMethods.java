package br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CompanyEntityMethods {
	private final CompanyEntity company;

	public CompanyEntityMethods() {
		this.company = null;
	}
}
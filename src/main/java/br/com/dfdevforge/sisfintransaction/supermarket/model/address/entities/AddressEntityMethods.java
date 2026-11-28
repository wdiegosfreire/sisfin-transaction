package br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AddressEntityMethods {
	private final AddressEntity company;

	public AddressEntityMethods() {
		this.company = null;
	}
}
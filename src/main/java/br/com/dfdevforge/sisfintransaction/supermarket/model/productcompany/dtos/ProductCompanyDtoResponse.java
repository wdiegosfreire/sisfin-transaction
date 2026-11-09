package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.dtos;

import lombok.Getter;
import lombok.Setter;

/*
 * DTO de saida (o que o frontend recebe).
 * E plano: expoe apenas os campos necessarios para a tela, sem objetos aninhados, portanto sem ciclos
 * e sem necessidade de @JsonIdentityInfo.
 */
@Getter
@Setter
public class ProductCompanyDtoResponse {
	private String identity;
	private String code;
	private String description;
	private String companyIdentity;
	private String companyName;
	private String companyFantasyName;
	private String productIdentity;
	private String productBarcode;
}

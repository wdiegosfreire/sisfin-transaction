package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.dtos;

import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import lombok.Getter;
import lombok.Setter;

/*
 * DTO de entrada (corpo das requisicoes de /productCompany).
 * E plano: os relacionamentos chegam apenas como identificadores e sao carregados do banco pelo
 * ProductCompanyDtoMapper. Implementa UserOwnedEntity para continuar passando pela checagem de
 * UserOwnershipRequestBodyAdvice.
 */
@Getter
@Setter
public class ProductCompanyDtoRequest implements UserOwnedEntity {
	private String identity;
	private String code;
	private String description;
	private String companyIdentity;
	private String productIdentity;
	private Long userIdentity;
}

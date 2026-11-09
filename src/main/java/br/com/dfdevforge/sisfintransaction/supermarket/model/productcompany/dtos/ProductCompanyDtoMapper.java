package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.dtos;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories.CompanyRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories.ProductRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import lombok.RequiredArgsConstructor;

/*
 * Converte DTO <-> entidade na fronteira (resource). Os services continuam trabalhando com entidades,
 * pois tambem sao chamados internamente (ex.: ObjectiveExecuteEditionService ao processar o cupom fiscal).
 */
@Component
@RequiredArgsConstructor
public class ProductCompanyDtoMapper {
	private final CompanyRepository companyRepository;
	private final ProductRepository productRepository;

	public ProductCompanyEntity toEntity(ProductCompanyDtoRequest dto) {
		ProductCompanyEntity entity = new ProductCompanyEntity();
		entity.setIdentity(StringUtils.trimToNull(dto.getIdentity()));
		entity.setCode(dto.getCode());
		entity.setDescription(dto.getDescription());
		entity.setUserIdentity(dto.getUserIdentity());

		if (StringUtils.isNotBlank(dto.getCompanyIdentity()))
			entity.setCompany(this.checkOwnership(this.companyRepository.findByIdentity(dto.getCompanyIdentity()), dto.getUserIdentity()));

		if (StringUtils.isNotBlank(dto.getProductIdentity()))
			entity.setProduct(this.checkOwnership(this.productRepository.findByIdentity(dto.getProductIdentity()), dto.getUserIdentity()));

		return entity;
	}

	public ProductCompanyDtoResponse toLeanDto(ProductCompanyEntity entity) {
		if (entity == null)
			return null;

		ProductCompanyDtoResponse dto = new ProductCompanyDtoResponse();
		dto.setIdentity(entity.getIdentity());
		dto.setCode(entity.getCode());
		dto.setDescription(entity.getDescription());

		CompanyEntity company = entity.getCompany();
		if (company != null) {
			dto.setCompanyIdentity(company.getIdentity());
			dto.setCompanyName(company.getName());
			dto.setCompanyFantasyName(company.getFantasyName());
		}

		ProductEntity product = entity.getProduct();
		if (product != null) {
			dto.setProductIdentity(product.getIdentity());
			dto.setProductBarcode(product.getBarcode());
		}

		return dto;
	}

	/*
	 * Converte os artefatos do mapa devolvido pelo service: ProductCompanyEntity (ou colecoes dela) viram
	 * ProductCompanyLeanDto; os demais valores passam inalterados.
	 */
	public Map<String, Object> toResponseMap(Map<String, Object> serviceResult) {
		Map<String, Object> response = new LinkedHashMap<>();

		serviceResult.forEach((key, value) -> response.put(key, this.convertArtifact(value)));

		return response;
	}

	private Object convertArtifact(Object value) {
		if (value instanceof ProductCompanyEntity)
			return this.toLeanDto((ProductCompanyEntity) value);

		if (value instanceof Collection && ((Collection<?>) value).stream().allMatch(ProductCompanyEntity.class::isInstance))
			return ((Collection<?>) value).stream().map(item -> this.toLeanDto((ProductCompanyEntity) item)).collect(Collectors.toList());

		return value;
	}

	/*
	 * Um id inexistente ou de outro usuario e tratado da mesma forma, para nao revelar se o registro existe.
	 */
	private <T extends UserOwnedEntity> T checkOwnership(T entity, Long userIdentity) {
		if (entity == null || userIdentity == null || !userIdentity.equals(entity.getUserIdentity()))
			throw new AccessDeniedException("The related entity does not belong to the authenticated user.");

		return entity;
	}
}

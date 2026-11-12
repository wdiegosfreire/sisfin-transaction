package br.com.dfdevforge.sisfintransaction.supermarket.model.product.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories.ProductRepository;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities.ProductInfoEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories.ProductInfoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequestScope
@RequiredArgsConstructor
public class ProductExecuteEditionService extends ProductBaseService implements CommonService {
	private final ProductRepository productRepository;
	private final ProductInfoRepository productInfoRepository;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProduct();
		this.saveProductInfoEdition();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productRegistered", this.productParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductEntity product = this.productRepository.findByIdentity(this.productParam.getIdentity());

		if (product == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProduct() {
		this.productRepository.save(this.productParam);
	}

	private void saveProductInfoEdition() {
		this.productInfoRepository.deleteAll(this.productInfoRepository.findByProductAndUserIdentityOrderByOrderAsc(this.productParam, this.productParam.getUserIdentity()));
		this.productInfoRepository.flush();

		for (ProductInfoEntity productInfoEdited : this.productParam.getProductInfoList()) {
			productInfoEdited.setIdentity(null);
			productInfoEdited.setOrder(productInfoEdited.getOrder());
			productInfoEdited.setDescription(productInfoEdited.getDescription());
			productInfoEdited.setProduct(this.productParam);
			productInfoEdited.setUserIdentity(this.productParam.getUserIdentity());
			this.productInfoRepository.save(productInfoEdited);
		}
	}
}
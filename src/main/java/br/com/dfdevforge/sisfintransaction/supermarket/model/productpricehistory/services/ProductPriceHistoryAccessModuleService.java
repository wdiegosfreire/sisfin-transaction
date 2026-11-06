package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.repositories.ProductPriceHistoryRepository;

@Service
@RequestScope
public class ProductPriceHistoryAccessModuleService extends ProductPriceHistoryBaseService implements CommonService {
	private final ProductPriceHistoryRepository productPriceHistoryRepository;

	@Autowired
	public ProductPriceHistoryAccessModuleService(ProductPriceHistoryRepository productPriceHistoryRepository) {
		this.productPriceHistoryRepository = productPriceHistoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findAllProductPriceHistories();
	}

	private void findAllProductPriceHistories() {
		this.setArtifact("productPriceHistoryList", this.productPriceHistoryRepository.findByUserIdentityOrderByPurchaseDateDesc(this.productPriceHistoryParam.getUserIdentity()));
	}
}
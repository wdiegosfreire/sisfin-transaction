package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForEditionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.repositories.ProductPriceHistoryRepository;

@Service
@RequestScope
public class ProductPriceHistoryAccessEditionService extends ProductPriceHistoryBaseService implements CommonService {
	private final ProductPriceHistoryRepository productPriceHistoryRepository;

	@Autowired
	public ProductPriceHistoryAccessEditionService(ProductPriceHistoryRepository productPriceHistoryRepository) {
		this.productPriceHistoryRepository = productPriceHistoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductPriceHistoryEntity productPriceHistory = this.productPriceHistoryRepository.findByIdentity(this.productPriceHistoryParam.getIdentity());

		if (productPriceHistory == null)
			throw new DataForEditionNotFoundException();

		this.setArtifact("productPriceHistory", productPriceHistory);
	}
}
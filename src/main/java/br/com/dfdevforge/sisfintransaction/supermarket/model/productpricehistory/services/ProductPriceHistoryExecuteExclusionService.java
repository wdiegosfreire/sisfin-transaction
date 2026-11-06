package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DataForExclusionNotFoundException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.repositories.ProductPriceHistoryRepository;

@Service
@RequestScope
public class ProductPriceHistoryExecuteExclusionService extends ProductPriceHistoryBaseService implements CommonService {
	private final ProductPriceHistoryRepository productPriceHistoryRepository;

	@Autowired
	public ProductPriceHistoryExecuteExclusionService(ProductPriceHistoryRepository productPriceHistoryRepository) {
		this.productPriceHistoryRepository = productPriceHistoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.deleteProductPriceHistory();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productPriceHistoryRegistered", this.productPriceHistoryParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForExclusionNotFoundException {
		ProductPriceHistoryEntity productPriceHistory = this.productPriceHistoryRepository.findByIdentity(this.productPriceHistoryParam.getIdentity());

		if (productPriceHistory == null)
			throw new DataForExclusionNotFoundException();
	}

	private void deleteProductPriceHistory() {
		this.productPriceHistoryRepository.delete(this.productPriceHistoryParam);
	}
}
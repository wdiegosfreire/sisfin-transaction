package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.services;

import java.util.Map;

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
public class ProductPriceHistoryExecuteEditionService extends ProductPriceHistoryBaseService implements CommonService {
	private final ProductPriceHistoryRepository productPriceHistoryRepository;

	@Autowired
	public ProductPriceHistoryExecuteEditionService(ProductPriceHistoryRepository productPriceHistoryRepository) {
		this.productPriceHistoryRepository = productPriceHistoryRepository;
	}

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findById();
		this.editProductPriceHistory();
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("productPriceHistoryRegistered", this.productPriceHistoryParam);
		return super.returnBusinessData();
	}

	private void findById() throws DataForEditionNotFoundException {
		ProductPriceHistoryEntity productPriceHistory = this.productPriceHistoryRepository.findByIdentity(this.productPriceHistoryParam.getIdentity());

		if (productPriceHistory == null)
			throw new DataForEditionNotFoundException();
	}

	private void editProductPriceHistory() {
		this.productPriceHistoryRepository.save(this.productPriceHistoryParam);
	}
}
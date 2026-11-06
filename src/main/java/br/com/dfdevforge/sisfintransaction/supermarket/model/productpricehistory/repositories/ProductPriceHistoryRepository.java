package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities.ProductPriceHistoryEntity;

@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistoryEntity, String> {
	public ProductPriceHistoryEntity findByIdentity(String identity);
	public List<ProductPriceHistoryEntity> findByUserIdentityOrderByPurchaseDateDesc(Long userIdentity);
	public List<ProductPriceHistoryEntity> findByProductCompanyAndUserIdentityOrderByPurchaseDateDesc(ProductCompanyEntity productCompany, Long userIdentity);
	public ProductPriceHistoryEntity findFirstByProductCompanyAndPurchaseDateAndUserIdentity(ProductCompanyEntity productCompany, Date purchaseDate, Long userIdentity);
}
package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities.ProductInfoEntity;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfoEntity, String> {
	public ProductInfoEntity findByIdentity(String identity);
	public List<ProductInfoEntity> findByUserIdentityOrderByOrderAsc(Long userIdentity);
	public List<ProductInfoEntity> findByProductAndUserIdentityOrderByOrderAsc(ProductEntity product, Long userIdentity);
}

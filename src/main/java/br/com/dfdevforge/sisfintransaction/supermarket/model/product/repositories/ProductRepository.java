package br.com.dfdevforge.sisfintransaction.supermarket.model.product.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
	public ProductEntity findByIdentity(String identity);
	public List<ProductEntity> findByUserIdentityOrderByProductCategoryAsc(Long userIdentity);
}
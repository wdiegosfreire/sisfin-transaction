package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, String> {
	public ProductCategoryEntity findByIdentity(String identity);
	public List<ProductCategoryEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
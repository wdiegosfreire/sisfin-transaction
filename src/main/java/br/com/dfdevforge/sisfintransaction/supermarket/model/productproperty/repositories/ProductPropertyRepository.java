package br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities.ProductPropertyEntity;

@Repository
public interface ProductPropertyRepository extends JpaRepository<ProductPropertyEntity, String> {
	public ProductPropertyEntity findByIdentity(String identity);
	public List<ProductPropertyEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
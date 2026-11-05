package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;

@Repository
public interface ProductCompanyRepository extends JpaRepository<ProductCompanyEntity, String> {
	public ProductCompanyEntity findByIdentity(String identity);
	public List<ProductCompanyEntity> findByUserIdentityOrderByDescriptionAsc(Long userIdentity);
}

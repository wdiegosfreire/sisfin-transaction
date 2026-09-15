package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, String> {
	public BrandEntity findByIdentity(String identity);
	public List<BrandEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
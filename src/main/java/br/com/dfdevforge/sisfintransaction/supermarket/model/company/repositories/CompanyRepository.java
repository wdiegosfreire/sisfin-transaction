package br.com.dfdevforge.sisfintransaction.supermarket.model.company.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
	public CompanyEntity findByIdentity(String identity);
	public List<CompanyEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
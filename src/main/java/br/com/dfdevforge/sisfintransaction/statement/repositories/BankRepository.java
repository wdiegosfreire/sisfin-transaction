package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.statement.entities.BankEntity;

public interface BankRepository extends JpaRepository<BankEntity, Long>, JpaSpecificationExecutor<BankEntity> {
	public BankEntity findByIdentity(Long identity);
	public List<BankEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
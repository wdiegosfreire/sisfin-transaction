package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;

public interface StatementRepository extends JpaRepository<StatementEntity, Long>, JpaSpecificationExecutor<StatementEntity> {
	public StatementEntity findByIdentity(Long identity);
	public List<StatementEntity> findByUserIdentityOrderByYearAscMonthAsc(Long userIdentity);
}
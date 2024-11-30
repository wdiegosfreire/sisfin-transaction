package br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.statement.model.statementpattern.entities.StatementPatternEntity;

public interface StatementPatternRepository extends JpaRepository<StatementPatternEntity, Long>, JpaSpecificationExecutor<StatementPatternEntity> {
	public StatementPatternEntity findByIdentity(Long identity);
	public List<StatementPatternEntity> findByUserIdentityOrderByComparatorAsc(Long userIdentity);
}
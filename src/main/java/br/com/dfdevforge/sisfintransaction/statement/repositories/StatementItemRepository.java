package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementItemEntity;

public interface StatementItemRepository extends JpaRepository<StatementItemEntity, Long>, JpaSpecificationExecutor<StatementItemEntity> {
	public StatementItemEntity findByIdentity(Long identity);
	public List<StatementItemEntity> findByUserIdentityOrderByMovementDateAsc(Long userIdentity);
	public List<StatementItemEntity> findByStatementOrderByMovementDateAscIdentityAsc(StatementEntity statement);

	public void deleteByStatement(StatementEntity statement);
}
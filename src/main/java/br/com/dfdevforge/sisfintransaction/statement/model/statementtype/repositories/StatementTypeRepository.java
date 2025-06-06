package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities.StatementTypeEntity;

public interface StatementTypeRepository extends JpaRepository<StatementTypeEntity, Long>, JpaSpecificationExecutor<StatementTypeEntity> {
	public StatementTypeEntity findByIdentity(Long identity);
	public List<StatementTypeEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
	public List<StatementTypeEntity> findByUserIdentityAndBankOrderByNameAsc(Long userIdentity, BankEntity bank);
}
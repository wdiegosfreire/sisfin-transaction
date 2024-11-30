package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities.StatementTypeEntity;

@Repository
public class StatementTypeRepositoryCustomized {
	private final EntityManager entityManager;

	public StatementTypeRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<StatementTypeEntity> searchInAllProperties(StatementTypeEntity statementType) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" stt.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   stt.name like :filter ");
		whereClause.append("   or stt.bank.name like :filter ");
		whereClause.append("   or stt.identity like :filter ");
		whereClause.append(" ) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select stt from StatementTypeEntity stt where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), StatementTypeEntity.class);

		query.setParameter("userIdentity", statementType.getUserIdentity());
		query.setParameter("filter", "%" + statementType.getFilter() + "%");
		
		return query.getResultList();
	}
}
package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;

@Repository
public class StatementPatternRepositoryCustomized {
	private final EntityManager entityManager;

	public StatementPatternRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<StatementPatternEntity> searchInAllProperties(StatementPatternEntity statementPattern) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" stp.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   stp.comparator like :filter ");
		whereClause.append("   or stp.description like :filter ");
		whereClause.append("   or stp.identity like :filter ");
		whereClause.append(" ) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select stp from StatementPatternEntity stp where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), StatementPatternEntity.class);

		query.setParameter("userIdentity", statementPattern.getUserIdentity());
		query.setParameter("filter", "%" + statementPattern.getFilter() + "%");
		
		return query.getResultList();
	}
}
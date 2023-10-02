package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;

@Repository
public class StatementRepositoryCustomized {
	private final EntityManager entityManager;

	public StatementRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<StatementEntity> searchInAllProperties(StatementEntity statement) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" sta.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   sta.statementType.name like :filter ");
		whereClause.append("   or sta.statementType.bank.name like :filter ");

		if (Utils.value.isNumber(statement.getFilter())) {
			whereClause.append(" or sta.year like :filter ");
			whereClause.append(" or sta.month like :filter ");
			whereClause.append(" or sta.identity like :filter ");
			whereClause.append(" or sta.openingBalance like :filter ");
			whereClause.append(" or sta.closingBalance like :filter ");
		}

		whereClause.append(" ) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select sta from StatementEntity as sta where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), StatementEntity.class);

		query.setParameter("userIdentity", statement.getUserIdentity());
		query.setParameter("filter", "%" + statement.getFilter() + "%");

		return query.getResultList();
	}
}
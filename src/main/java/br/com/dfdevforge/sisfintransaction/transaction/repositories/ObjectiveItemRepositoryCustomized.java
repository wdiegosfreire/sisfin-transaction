package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;

@Repository
public class ObjectiveItemRepositoryCustomized {
	private static final String USER_IDENTITY = "userIdentity";

	private final EntityManager entityManager;

	public ObjectiveItemRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<AccountEntity> searchInAllProperties(AccountEntity account) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" acc.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   acc.name like :filter ");
		whereClause.append("   or acc.level like :filter ");
		whereClause.append("   or acc.note like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(account.getFilter()))
			whereClause.append(" or acc.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select acc from AccountEntity as acc where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter(USER_IDENTITY, account.getUserIdentity());
		query.setParameter("filter", "%" + account.getFilter() + "%");
		
		return query.getResultList();
	}
}
package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.entities.AccountEntity;

@Repository
public class AccountRepositoryCustomized {
	private final EntityManager entityManager;

	public AccountRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<AccountEntity> searchInAllProperties(AccountEntity account) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" or acc.name like :filter ");
		whereClause.append(" or acc.level like :filter ");
		whereClause.append(" or acc.note like :filter ");

		if (Utils.value.isNumber(account.getFilter()))
			whereClause.append(" or acc.identity like :filter ");

		if (whereClause.substring(0, 4).equals(" or ") || whereClause.substring(0, 5).equals(" and "))
			whereClause.replace(0, 4, " where ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select acc from AccountEntity as acc " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter("filter", "%" + account.getFilter() + "%");
		
		return query.getResultList();
	}

	public List<AccountEntity> searchAllWithLevel(Integer level) {
		if (level == 1)
			level = 3;
		else if (level == 2)
			level = 6;
		else if (level == 3)
			level = 9;

		StringBuilder jpql = new StringBuilder();

		jpql.append("select acc from AccountEntity as acc ");
		jpql.append("where ");
		jpql.append("  length(acc.level) = :level ");

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter("level", level);

		return query.getResultList();
	}

	public List<AccountEntity> searchAllWithParent(Long accountIdentityParent) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("select acc from AccountEntity as acc ");
		jpql.append("where ");
		jpql.append("  acc.accountParent.identity = :accountIdentityParent ");

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter("accountIdentityParent", accountIdentityParent);

		return query.getResultList();
	}
}
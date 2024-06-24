package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;

@Repository
public class AccountRepositoryCustomized {
	private static final String SELECT_DEFAULT_PREFIX = "select acc from AccountEntity as acc ";
	private static final String WHERE = "where ";
	private static final String USER_IDENTITY_CRITERIA = "  acc.userIdentity = :userIdentity ";

	private static final String USER_IDENTITY = "userIdentity";

	private final EntityManager entityManager;

	public AccountRepositoryCustomized(EntityManager entityManager) {
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

	public List<AccountEntity> searchAllWithLevel(Integer level, long userIdentity) {
		if (level == 1)
			level = 3;
		else if (level == 2)
			level = 6;
		else if (level == 3)
			level = 9;

		StringBuilder jpql = new StringBuilder();

		jpql.append(SELECT_DEFAULT_PREFIX);
		jpql.append(WHERE);
		jpql.append(   USER_IDENTITY_CRITERIA);
		jpql.append("  and length(acc.level) = :level ");

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter(USER_IDENTITY, userIdentity);
		query.setParameter("level", level);

		return query.getResultList();
	}

	public List<AccountEntity> searchAllWithParent(long accountIdentityParent, long userIdentity) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(SELECT_DEFAULT_PREFIX);
		jpql.append(WHERE);
		jpql.append(   USER_IDENTITY_CRITERIA);
		jpql.append("  and acc.accountParent.identity = :accountIdentityParent ");

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter(USER_IDENTITY, userIdentity);
		query.setParameter("accountIdentityParent", accountIdentityParent);

		return query.getResultList();
	}
}
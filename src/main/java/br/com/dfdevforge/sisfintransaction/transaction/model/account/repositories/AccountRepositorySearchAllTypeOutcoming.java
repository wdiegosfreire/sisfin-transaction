package br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;

@Repository
public class AccountRepositorySearchAllTypeOutcoming {
	private static final String USER_IDENTITY = "userIdentity";

	private final EntityManager entityManager;

	public AccountRepositorySearchAllTypeOutcoming(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<AccountEntity> execute(long userIdentity) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("select acc ");
		jpql.append("from ");
		jpql.append("  AccountEntity as acc ");
		jpql.append("where ");
		jpql.append("  acc.userIdentity = :userIdentity ");
		jpql.append("  and acc.level like '03%' ");
		jpql.append("  and length(acc.level) < 9 ");
		jpql.append("order by ");
		jpql.append("  acc.level ");

		var query = this.entityManager.createQuery(jpql.toString(), AccountEntity.class);

		query.setParameter(USER_IDENTITY, userIdentity);

		return query.getResultList();
	}
}
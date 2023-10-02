package br.com.dfdevforge.sisfintransaction.statement.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.statement.entities.BankEntity;

@Repository
public class BankRepositoryCustomized {
	private final EntityManager entityManager;

	public BankRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<BankEntity> searchInAllProperties(BankEntity bank) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" ban.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   ban.name like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(bank.getFilter()))
			whereClause.append(" or ban.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select ban from BankEntity as ban where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), BankEntity.class);

		query.setParameter("userIdentity", bank.getUserIdentity());
		query.setParameter("filter", "%" + bank.getFilter() + "%");
		
		return query.getResultList();
	}
}
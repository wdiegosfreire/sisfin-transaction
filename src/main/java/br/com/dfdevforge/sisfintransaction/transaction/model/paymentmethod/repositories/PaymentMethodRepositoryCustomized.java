package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;

@Repository
public class PaymentMethodRepositoryCustomized {
	private final EntityManager entityManager;

	public PaymentMethodRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<PaymentMethodEntity> searchInAllProperties(PaymentMethodEntity paymentMethod) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" pam.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   pam.name like :filter ");
		whereClause.append("   or pam.acronym like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(paymentMethod.getFilter()))
			whereClause.append(" or pam.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select pam from PaymentMethodEntity as pam where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), PaymentMethodEntity.class);

		query.setParameter("userIdentity", paymentMethod.getUserIdentity());
		query.setParameter("filter", "%" + paymentMethod.getFilter() + "%");
		
		return query.getResultList();
	}
}
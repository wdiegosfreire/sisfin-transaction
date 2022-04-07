package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.entities.PaymentMethodEntity;

@Repository
public class PaymentMethodRepositoryCustomized {
	private final EntityManager entityManager;

	public PaymentMethodRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<PaymentMethodEntity> searchInAllProperties(PaymentMethodEntity paymentMethod) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" or pam.name like :filter ");
		whereClause.append(" or pam.acronym like :filter ");

		if (Utils.value.isNumber(paymentMethod.getFilter()))
			whereClause.append(" or pam.identity like :filter ");

		if (whereClause.substring(0, 4).equals(" or ") || whereClause.substring(0, 5).equals(" and "))
			whereClause.replace(0, 4, " where ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select pam from PaymentMethodEntity as pam " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), PaymentMethodEntity.class);

		query.setParameter("filter", "%" + paymentMethod.getFilter() + "%");
		
		return query.getResultList();
	}
}
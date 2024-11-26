package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;

@Repository
public class ObjectiveMovementRepositorySelectByDueDateOrPaymentDateOrValue {
	private final EntityManager entityManager;

	public ObjectiveMovementRepositorySelectByDueDateOrPaymentDateOrValue(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<ObjectiveMovementEntity> execute(Date targetDate, BigDecimal targetValue, Long userIdentity) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" obm.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   (obm.paymentDate is not null and obm.paymentDate = :targetDate) or ");
		whereClause.append("   (obm.paymentDate is null and obm.dueDate = :targetDate) ");
		whereClause.append(" ) ");
		whereClause.append(" and obm.value = :targetValue ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select obm from ObjectiveMovementEntity as obm where " + whereClause + " order by obm.paymentDate ");

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveMovementEntity.class);

		query.setParameter("userIdentity", userIdentity);
		query.setParameter("targetDate", targetDate);
		query.setParameter("targetValue", targetValue);

		return query.getResultList();
	}
}
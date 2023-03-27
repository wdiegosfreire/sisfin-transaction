package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;

@Repository
public class ObjectiveMovementRepositoryCustomized {
	private final EntityManager entityManager;

	public ObjectiveMovementRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<ObjectiveMovementEntity> searchByPeriod(ObjectiveMovementEntity objectiveMovement) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" obm.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   (obm.paymentDate is not null and obm.paymentDate between :startDate and :endDate) or ");
		whereClause.append("   (obm.paymentDate is null and obm.dueDate between :startDate and :endDate) ");
		whereClause.append(" ) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select obm from ObjectiveMovementEntity as obm where " + whereClause + " order by obm.paymentDate ");

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveMovementEntity.class);

		query.setParameter("userIdentity", objectiveMovement.getUserIdentity());
		query.setParameter("startDate", this.findStartDate(objectiveMovement));
		query.setParameter("endDate", this.findEndDate(objectiveMovement));

		return query.getResultList();
	}

	public List<ObjectiveMovementEntity> searchInAllPropertiesByPeriod(ObjectiveMovementEntity objectiveMovement) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" obm.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   (obm.paymentDate is not null and obm.paymentDate between :startDate and :endDate) or ");
		whereClause.append("   (obm.paymentDate is null and obm.dueDate between :startDate and :endDate) ");
		whereClause.append(" ) ");

		whereClause.append(" and ( ");
		whereClause.append("   cast(obm.identity as string) like :filter ");
		whereClause.append("   or cast(obm.value as string) like :filter ");
		whereClause.append("   or cast(obm.installment as string) like :filter ");
		whereClause.append("   or obm.objective.description like :filter ");
		whereClause.append("   or obm.objective.location.name like :filter ");
		whereClause.append(" ) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select obm from ObjectiveMovementEntity as obm where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveMovementEntity.class);

		query.setParameter("userIdentity", objectiveMovement.getUserIdentity());
		query.setParameter("startDate", this.findStartDate(objectiveMovement));
		query.setParameter("endDate", this.findEndDate(objectiveMovement));
		query.setParameter("filter", "%" + objectiveMovement.getFilter() + "%");

		return query.getResultList();
	}

	private Date findStartDate(ObjectiveMovementEntity objectiveMovement) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getFirstDayOfMonth(objectiveMovement.getPaymentDate()));
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}

	private Date findEndDate(ObjectiveMovementEntity objectiveMovement) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getLastDayOfMonth(objectiveMovement.getPaymentDate()));
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}
}
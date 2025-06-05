package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.repositories;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities.ObjectiveMovementEntity;

@Repository
public class ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters {
	private static final String LOCATION_IDENTITY = "locationIdentity";
	private static final String ACCOUNT_SOURCE_IDENTITY = "accountSourceIdentity";
	private static final String VALUE_START = "valueStart";
	private static final String VALUE_END = "valueEnd";

	private final EntityManager entityManager;

	public ObjectiveMovementRepositorySelectByPeriodAndDynamicFilters(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<ObjectiveMovementEntity> execute(Map<String, String> filterMap, Long userIdentity) {
		Instant instant = Instant.parse(filterMap.get("periodDate"));
		Date periodDate = Date.from(instant);

		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" obm.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   (obm.paymentDate is not null and obm.paymentDate between :startDate and :endDate) or ");
		whereClause.append("   (obm.paymentDate is null and obm.dueDate between :startDate and :endDate) ");
		whereClause.append(" ) ");

		whereClause.append(" and (obm.accountSource.identity = :accountSourceIdentity or :accountSourceIdentity is null) ");
		whereClause.append(" and (obm.value >= :valueStart or :valueStart is null) ");
		whereClause.append(" and (obm.value <= :valueEnd or :valueEnd is null) ");
		whereClause.append(" and (obm.objective.location.identity = :locationIdentity or :locationIdentity is null) ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select obm from ObjectiveMovementEntity as obm where " + whereClause + " order by obm.paymentDate ");

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveMovementEntity.class);

		query.setParameter("userIdentity", userIdentity);
		query.setParameter("startDate", this.findStartDate(periodDate));
		query.setParameter("endDate", this.findEndDate(periodDate));
		query.setParameter(ACCOUNT_SOURCE_IDENTITY, Utils.value.exists(filterMap, ACCOUNT_SOURCE_IDENTITY) ? Long.parseLong(filterMap.get(ACCOUNT_SOURCE_IDENTITY)) : null);
		query.setParameter(VALUE_START, Utils.value.exists(filterMap, VALUE_START) ? new BigDecimal(filterMap.get(VALUE_START)) : null);
		query.setParameter(VALUE_END, Utils.value.exists(filterMap, VALUE_END) ? new BigDecimal(filterMap.get(VALUE_END)) : null);
		query.setParameter(LOCATION_IDENTITY, Utils.value.exists(filterMap, LOCATION_IDENTITY) ? Long.parseLong(filterMap.get(LOCATION_IDENTITY)) : null);

		return query.getResultList();
	}

	private Date findStartDate(Date periodDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getFirstDayOfMonth(periodDate));
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}

	private Date findEndDate(Date periodDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Utils.date.getLastDayOfMonth(periodDate));
		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		return calendar.getTime();
	}
}
package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;

@Repository
public class SummaryRepositoryCustomized {
	private final EntityManager entityManager;

	public SummaryRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public BigDecimal getIncomingData(Date periodDate, Long userIdentity) {
		StringBuilder inClause = new StringBuilder();
		inClause.append("select ");
		inClause.append("  acc.acc_identity ");
		inClause.append("from ");
		inClause.append("  acc_account acc ");
		inClause.append("where ");
		inClause.append("  acc.usr_identity = :userIdentity ");
		inClause.append("  and acc.acc_level like '01%' ");
		inClause.append("  and length(acc.acc_level) = 9 ");

		StringBuilder jpql = new StringBuilder();
		jpql.append("select ");
		jpql.append("  sum(obi.obi_unitary_value) obi_total_value ");
		jpql.append("from ");
		jpql.append("  obj_objective obj inner join ");
		jpql.append("  obm_objective_movement obm on obm.obj_identity = obj.obj_identity inner join ");
		jpql.append("  obi_objective_item obi on obi.obj_identity = obj.obj_identity ");
		jpql.append("where ");
		jpql.append("  obi.usr_identity = :userIdentity ");
		jpql.append("  and obi.acc_identity_target in (" + inClause + ") ");
		jpql.append("  and obm.acc_identity_source != obi.acc_identity_target ");
		jpql.append("  and ( ");
		jpql.append("    (obm.obm_payment_date is not null and obm.obm_payment_date between :startDate and :endDate) or ");
		jpql.append("    (obm.obm_payment_date is null and obm.obm_due_date between :startDate and :endDate) ");
		jpql.append("  ) ");

		var query = this.entityManager.createNativeQuery(jpql.toString());

		query.setParameter("userIdentity", userIdentity);
		query.setParameter("startDate", this.findStartDate(periodDate));
		query.setParameter("endDate", this.findEndDate(periodDate));

		return query.getSingleResult() != null ? (BigDecimal) query.getSingleResult() : new BigDecimal(0);
	}

	public BigDecimal getOutcomingData(Date periodDate, Long userIdentity) {
		StringBuilder inClause = new StringBuilder();
		inClause.append("select ");
		inClause.append("  acc.acc_identity ");
		inClause.append("from ");
		inClause.append("  acc_account acc ");
		inClause.append("where ");
		inClause.append("  acc.usr_identity = :userIdentity ");
		inClause.append("  and acc.acc_level like '01%' ");
		inClause.append("  and length(acc.acc_level) = 9 ");
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("select ");
		jpql.append("  sum(obm.obm_value) obm_total_value  ");
		jpql.append("from ");
		jpql.append("  obj_objective obj inner join ");
		jpql.append("  obm_objective_movement obm on obm.obj_identity = obj.obj_identity inner join ");
		jpql.append("  obi_objective_item obi on obi.obj_identity = obj.obj_identity ");
		jpql.append("where ");
		jpql.append("  obi.usr_identity = :userIdentity ");
		jpql.append("  and obm.acc_identity_source in (" + inClause + ") ");
		jpql.append("  and obm.acc_identity_source != obi.acc_identity_target ");
		jpql.append("  and ( ");
		jpql.append("    (obm.obm_payment_date is not null and obm.obm_payment_date between :startDate and :endDate) or ");
		jpql.append("    (obm.obm_payment_date is null and obm.obm_due_date between :startDate and :endDate) ");
		jpql.append("  ) ");
		
		var query = this.entityManager.createNativeQuery(jpql.toString());
		
		query.setParameter("userIdentity", userIdentity);
		query.setParameter("startDate", this.findStartDate(periodDate));
		query.setParameter("endDate", this.findEndDate(periodDate));
		
		return query.getSingleResult() != null ? (BigDecimal) query.getSingleResult() : new BigDecimal(0);
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
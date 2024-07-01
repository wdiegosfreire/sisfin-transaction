package br.com.dfdevforge.sisfintransaction.transaction.repositories.summary;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.persistences.BasePersistence;

@Repository
public class SummaryRepositorySelectTotalOutcomingByBalanceAccount extends BasePersistence {
	private final EntityManager entityManager;

	public SummaryRepositorySelectTotalOutcomingByBalanceAccount(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public BigDecimal execute(Date periodDate, Long accountIdentity, Long userIdentity) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("select ");
		jpql.append("  sum(obm.obm_value) obm_total_value ");
		jpql.append("from ");
		jpql.append("  obj_objective obj inner join ");
		jpql.append("  obm_objective_movement obm on obm.obj_identity = obj.obj_identity inner join ");
		jpql.append("  obi_objective_item obi on obi.obj_identity = obj.obj_identity ");
		jpql.append("where ");
		jpql.append("  obj.usr_identity = :userIdentity ");
		jpql.append("  and obm.acc_identity_source = :accountIdentity ");
		jpql.append("  and obm.acc_identity_source <> obi.acc_identity_target ");
		jpql.append("  and ( ");
		jpql.append("    (obm.obm_payment_date is not null and obm.obm_payment_date between :startDate and :endDate) or ");
		jpql.append("    (obm.obm_payment_date is null and obm.obm_due_date between :startDate and :endDate) ");
		jpql.append("  ) ");
		
		var query = this.entityManager.createNativeQuery(jpql.toString());
		
		query.setParameter("userIdentity", userIdentity);
		query.setParameter("accountIdentity", accountIdentity);
		query.setParameter("startDate", this.findStartDate(periodDate));
		query.setParameter("endDate", this.findEndDate(periodDate));
		
		return query.getSingleResult() != null ? (BigDecimal) query.getSingleResult() : new BigDecimal(0);
	}
}
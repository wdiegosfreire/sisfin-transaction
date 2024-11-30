package br.com.dfdevforge.sisfintransaction.transaction.model.summary.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.persistences.BasePersistence;

@Repository
public class SummaryRepositorySelectTotalByOutcomingAccount extends BasePersistence {
	private final EntityManager entityManager;

	public SummaryRepositorySelectTotalByOutcomingAccount(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	public List<Tuple> execute(Date periodDate, String accountLevel, Long userIdentity) {
		StringBuilder jpql = new StringBuilder();

		String parent = "";
		if (accountLevel.length() == 3)
			parent = "_parent";

		jpql.append("select ");
		jpql.append("  acc.acc_identity, ");
		jpql.append("  acc.acc_name, ");
		jpql.append("  acc.acc_level, ");
		jpql.append("  case when tst.obi_total_value is null then 0 else tst.obi_total_value end obi_total_value  ");
		jpql.append("from ");
		jpql.append("  acc_account acc left join  ");
		jpql.append("  ( ");
		jpql.append("    select ");
		jpql.append("      acc_i.acc_identity" + parent + ", ");
		jpql.append("      sum(obi_i.obi_unitary_value * obi_i.obi_amount) obi_total_value ");
		jpql.append("    from ");
		jpql.append("      obj_objective obj_i inner join ");
		jpql.append("      obm_objective_movement obm_i on obm_i.obj_identity = obj_i.obj_identity inner join ");
		jpql.append("      obi_objective_item obi_i on obi_i.obj_identity = obj_i.obj_identity inner join ");
		jpql.append("      acc_account acc_i on obi_i.acc_identity_target = acc_i.acc_identity ");
		jpql.append("    where ");
		jpql.append("      obi_i.usr_identity = :userIdentity ");
		jpql.append("      and ( ");
		jpql.append("        (obm_i.obm_payment_date is not null and obm_i.obm_payment_date between :startDate and :endDate) or ");
		jpql.append("        (obm_i.obm_payment_date is null and obm_i.obm_due_date between :startDate and :endDate) ");
		jpql.append("      ) ");
		jpql.append("    group by ");
		jpql.append("      acc_i.acc_identity" + parent + " ");
		jpql.append("  ) tst on acc.acc_identity = tst.acc_identity" + parent + " ");
		jpql.append("where ");
		jpql.append("  acc.acc_level like :accountLevel ");
		jpql.append("  and length(acc.acc_level) = :accountLength ");

		var query = this.entityManager.createNativeQuery(jpql.toString(), Tuple.class);

		query.setParameter("userIdentity", userIdentity);
		query.setParameter("accountLevel", accountLevel + "%");
		query.setParameter("accountLength", accountLevel.length() + 3);
		query.setParameter("startDate", this.findStartDate(periodDate));
		query.setParameter("endDate", this.findEndDate(periodDate));

		return query.getResultList();
	}
}
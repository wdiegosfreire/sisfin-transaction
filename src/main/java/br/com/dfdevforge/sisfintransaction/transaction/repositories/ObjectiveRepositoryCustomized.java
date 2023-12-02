package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;

@Repository
public class ObjectiveRepositoryCustomized {
	private final EntityManager entityManager;

	public ObjectiveRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<ObjectiveEntity> searchByPeriod(ObjectiveEntity objective) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("select ");
		jpql.append("  objective ");
		jpql.append("from ");
		jpql.append("  ObjectiveEntity as objective ");
		jpql.append("where ");
		jpql.append("  objective.userIdentity = :userIdentity ");

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveEntity.class);

		query.setParameter("userIdentity", objective.getUserIdentity());

		return query.getResultList();
	}

	public List<ObjectiveEntity> searchInAllProperties(ObjectiveEntity objective) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" loc.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   loc.cnpj like :filter ");
		whereClause.append("   or loc.name like :filter ");
		whereClause.append("   or loc.branch like :filter ");
		whereClause.append("   or loc.note like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(objective.getFilter()))
			whereClause.append(" or loc.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select loc from LocationEntity as loc where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveEntity.class);

		query.setParameter("userIdentity", objective.getUserIdentity());
		query.setParameter("filter", "%" + objective.getFilter() + "%");
		
		return query.getResultList();
	}
}
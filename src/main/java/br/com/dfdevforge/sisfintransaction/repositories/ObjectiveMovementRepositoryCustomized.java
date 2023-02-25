package br.com.dfdevforge.sisfintransaction.repositories;

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

	public List<ObjectiveMovementEntity> searchInAllProperties(ObjectiveMovementEntity objectiveMovement) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" loc.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   loc.cnpj like :filter ");
		whereClause.append("   or loc.name like :filter ");
		whereClause.append("   or loc.branch like :filter ");
		whereClause.append("   or loc.note like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(objectiveMovement.getFilter()))
			whereClause.append(" or loc.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select loc from ObjectiveMovementEntity as loc where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), ObjectiveMovementEntity.class);

		query.setParameter("userIdentity", objectiveMovement.getUserIdentity());
		query.setParameter("filter", "%" + objectiveMovement.getFilter() + "%");
		
		return query.getResultList();
	}
}
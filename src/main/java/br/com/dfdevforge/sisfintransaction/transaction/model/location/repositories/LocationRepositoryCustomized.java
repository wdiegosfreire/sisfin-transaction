package br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.model.location.entities.LocationEntity;

@Repository
public class LocationRepositoryCustomized {
	private final EntityManager entityManager;

	public LocationRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<LocationEntity> searchInAllProperties(LocationEntity location) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" loc.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   loc.name like :filter ");
		whereClause.append("   or loc.note like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(location.getFilter()))
			whereClause.append(" or loc.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select loc from LocationEntity as loc where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), LocationEntity.class);

		query.setParameter("userIdentity", location.getUserIdentity());
		query.setParameter("filter", "%" + location.getFilter() + "%");
		
		return query.getResultList();
	}
}
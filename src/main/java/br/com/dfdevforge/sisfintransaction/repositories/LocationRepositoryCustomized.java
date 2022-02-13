package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.common.util.Utils;
import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;

@Repository
public class LocationRepositoryCustomized {
	private final EntityManager entityManager;

	public LocationRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<LocationEntity> searchInAllProperties(LocationEntity location) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" or loc.cnpj like :filter ");
		whereClause.append(" or loc.name like :filter ");
		whereClause.append(" or loc.branch like :filter ");
		whereClause.append(" or loc.note like :filter ");

		if (Utils.value.isNumber(location.getFilter()))
			whereClause.append(" or loc.identity like :filter ");

		if (whereClause.substring(0, 4).equals(" or ") || whereClause.substring(0, 5).equals(" and "))
			whereClause.replace(0, 4, " where ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select loc from LocationEntity as loc " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), LocationEntity.class);

		query.setParameter("filter", "%" + location.getFilter() + "%");
		
		return query.getResultList();
	}
}
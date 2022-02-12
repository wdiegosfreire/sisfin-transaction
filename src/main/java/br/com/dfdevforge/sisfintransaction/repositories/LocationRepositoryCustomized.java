package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;

@Repository
public class LocationRepositoryCustomized {
	private final EntityManager entityManager;

	public LocationRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<LocationEntity> searchInAllProperties(LocationEntity location) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("select loc from LocationEntity as loc ");
		jpql.append("where  ");
//		jpql.append("  loc.identity like :filter or ");
		jpql.append("  loc.cnpj like :filter or ");
		jpql.append("  loc.name like :filter or ");
		jpql.append("  loc.branch like :filter or ");
		jpql.append("  loc.note like :filter ");

		var query = this.entityManager.createQuery(jpql.toString(), LocationEntity.class);

		query.setParameter("filter", "%" + location.getFilter() + "%");
		
		return query.getResultList();
	}
}
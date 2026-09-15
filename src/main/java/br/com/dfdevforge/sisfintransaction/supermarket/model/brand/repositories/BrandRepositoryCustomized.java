package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;

@Repository
public class BrandRepositoryCustomized {
	private final EntityManager entityManager;

	public BrandRepositoryCustomized(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<BrandEntity> searchInAllProperties(BrandEntity brand) {
		StringBuilder whereClause = new StringBuilder();

		whereClause.append(" bra.userIdentity = :userIdentity ");

		whereClause.append(" and ( ");
		whereClause.append("   bra.name like :filter ");
		whereClause.append("   or bra.note like :filter ");
		whereClause.append(" ) ");

		if (Utils.value.isNumber(brand.getFilter()))
			whereClause.append(" or bra.identity like :filter ");

		StringBuilder jpql = new StringBuilder();

		jpql.append("select bra from BrandEntity as bra where " + whereClause);

		var query = this.entityManager.createQuery(jpql.toString(), BrandEntity.class);

		query.setParameter("userIdentity", brand.getUserIdentity());
		query.setParameter("filter", "%" + brand.getFilter() + "%");
		
		return query.getResultList();
	}
}
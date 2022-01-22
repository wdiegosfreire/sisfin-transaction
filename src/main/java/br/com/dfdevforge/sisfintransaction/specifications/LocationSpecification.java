package br.com.dfdevforge.sisfintransaction.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;

public class LocationSpecification {
	public static Specification<LocationEntity> searchInAllProperties(Object searchParam) {
		return new Specification<LocationEntity>() {
			private static final long serialVersionUID = 1L;

			public Predicate toPredicate(Root<LocationEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//				Predicate predicateIdentity = builder.equal(root.get("identity"), searchParam);
				Predicate predicateCnpj = builder.equal(root.get("cnpj"), searchParam);
				Predicate predicateName = builder.equal(root.get("name"), searchParam);
				Predicate predicateBranch = builder.equal(root.get("branch"), searchParam);
				Predicate predicateNote = builder.equal(root.get("note"), searchParam);

				return builder.or(predicateCnpj, predicateName, predicateBranch, predicateNote);
			}
		};
	}
}
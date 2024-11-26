package br.com.dfdevforge.sisfintransaction.transaction.model.location.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.model.location.entities.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
	public LocationEntity findByIdentity(Long identity);
	public List<LocationEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
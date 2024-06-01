package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
	public LocationEntity findByIdentity(Long identity);
	public List<LocationEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
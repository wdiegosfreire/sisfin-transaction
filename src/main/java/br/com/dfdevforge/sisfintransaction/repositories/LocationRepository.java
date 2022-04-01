package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long>, JpaSpecificationExecutor<LocationEntity> {
	public LocationEntity findByIdentity(Long identity);
	public List<LocationEntity> findByUserIdentity(Long userIdentity);
}
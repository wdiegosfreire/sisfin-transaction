package br.com.dfdevforge.sisfintransaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.entities.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
	public LocationEntity findByIdentity(Long identity);
	public LocationEntity findByName(String name);
}
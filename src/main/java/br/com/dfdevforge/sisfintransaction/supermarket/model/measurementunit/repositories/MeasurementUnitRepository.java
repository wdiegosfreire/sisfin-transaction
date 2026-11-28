package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;

@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnitEntity, String> {
	public MeasurementUnitEntity findByIdentity(String identity);
	public List<MeasurementUnitEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
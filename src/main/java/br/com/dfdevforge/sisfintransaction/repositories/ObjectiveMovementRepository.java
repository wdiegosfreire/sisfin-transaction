package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.entities.ObjectiveMovementEntity;

public interface ObjectiveMovementRepository extends JpaRepository<ObjectiveMovementEntity, Long>, JpaSpecificationExecutor<ObjectiveMovementEntity> {
	public ObjectiveMovementEntity findByIdentity(Long identity);
	public List<ObjectiveMovementEntity> findByUserIdentity(Long userIdentity);
}
package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.entities.ObjectiveItemEntity;

public interface ObjectiveItemRepository extends JpaRepository<ObjectiveItemEntity, Long>, JpaSpecificationExecutor<ObjectiveItemEntity> {
	public ObjectiveItemEntity findByIdentity(Long identity);
	public List<ObjectiveItemEntity> findByUserIdentity(Long userIdentity);
	public List<ObjectiveItemEntity> findByObjective(ObjectiveEntity objective);
}
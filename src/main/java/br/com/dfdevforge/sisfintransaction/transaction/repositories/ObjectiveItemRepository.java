package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;

public interface ObjectiveItemRepository extends JpaRepository<ObjectiveItemEntity, Long> {
	public ObjectiveItemEntity findByIdentity(Long identity);
	public List<ObjectiveItemEntity> findByUserIdentity(Long userIdentity);
	public List<ObjectiveItemEntity> findByObjective(ObjectiveEntity objective);

	public void deleteByObjective(ObjectiveEntity objective);
}
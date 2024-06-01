package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;

public interface ObjectiveMovementRepository extends JpaRepository<ObjectiveMovementEntity, Long> {
	public ObjectiveMovementEntity findByIdentity(Long identity);
	public List<ObjectiveMovementEntity> findByUserIdentity(Long userIdentity);
	public List<ObjectiveMovementEntity> findByObjective(ObjectiveEntity objective);
	public List<ObjectiveMovementEntity> findByUserIdentityAndPaymentDateBetween(Long userIdentity, Date startDate, Date endDate);

	public void deleteByObjective(ObjectiveEntity objective);
}
package br.com.dfdevforge.sisfintransaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.entities.ObjectiveEntity;

public interface ObjectiveRepository extends JpaRepository<ObjectiveEntity, Long> {
	public ObjectiveEntity findByIdentity(Long identity);
	public List<ObjectiveEntity> findByUserIdentity(Long userIdentity);

	public List<ObjectiveEntity> findByIdentityIn(List<Long> identityList);
}
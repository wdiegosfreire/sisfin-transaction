package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
	public AccountEntity findByIdentity(Long identity);
	public List<AccountEntity> findByUserIdentity(Long userIdentity);
	public List<AccountEntity> findByUserIdentityOrderByLevel(Long userIdentity);
}
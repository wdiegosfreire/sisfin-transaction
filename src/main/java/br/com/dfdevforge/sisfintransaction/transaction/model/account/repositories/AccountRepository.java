package br.com.dfdevforge.sisfintransaction.transaction.model.account.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
	public AccountEntity findByIdentity(Long identity);
	public List<AccountEntity> findByUserIdentity(Long userIdentity);
	public List<AccountEntity> findByUserIdentityOrderByLevel(Long userIdentity);
}
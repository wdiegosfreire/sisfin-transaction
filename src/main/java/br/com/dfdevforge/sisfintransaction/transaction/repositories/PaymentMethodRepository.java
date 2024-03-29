package br.com.dfdevforge.sisfintransaction.transaction.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long>, JpaSpecificationExecutor<PaymentMethodEntity> {
	public PaymentMethodEntity findByIdentity(Long identity);
	public List<PaymentMethodEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
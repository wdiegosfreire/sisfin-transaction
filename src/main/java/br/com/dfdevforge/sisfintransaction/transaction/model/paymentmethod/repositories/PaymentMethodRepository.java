package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
	public PaymentMethodEntity findByIdentity(Long identity);
	public List<PaymentMethodEntity> findByUserIdentityOrderByNameAsc(Long userIdentity);
}
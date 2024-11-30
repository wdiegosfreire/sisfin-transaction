package br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "pam_payment_method")
public class PaymentMethodEntity extends BaseEntity {
	@Id
	@Column(name = "pam_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "pan_name")
	private String name;

	@Column(name = "pan_acronym")
	@Length(max = 3, message = "Test Message Xyz")
	private String acronym;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
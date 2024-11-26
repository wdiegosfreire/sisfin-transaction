package br.com.dfdevforge.sisfintransaction.transaction.model.objectivemovement.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.paymentmethod.entities.PaymentMethodEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "obm_objective_movement")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class ObjectiveMovementEntity extends BaseEntity {
	@Id
	@Column(name = "obm_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "obm_registration_date")
	private Date registrationDate;

	@Column(name = "obm_due_date")
	private Date dueDate;

	@Column(name = "obm_payment_date")
	private Date paymentDate;

	@Column(name = "obm_value")
	private BigDecimal value;

	@Column(name = "obm_installment")
	private Integer installment;

	@ManyToOne
	@JoinColumn(name = "obj_identity")
	private ObjectiveEntity objective;

	@ManyToOne
	@JoinColumn(name = "pam_identity")
	private PaymentMethodEntity paymentMethod;

	@ManyToOne
	@JoinColumn(name = "acc_identity_source")
	private AccountEntity accountSource;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	private boolean isInPeriod;

	@Transient
	private ObjectiveMovementEntityProps props = new ObjectiveMovementEntityProps();
}
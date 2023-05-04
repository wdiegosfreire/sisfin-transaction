package br.com.dfdevforge.sisfintransaction.entities;

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

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "obm_objective_movement")
@EqualsAndHashCode(callSuper=false, of={"identity"})
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

	@Column(name = "usr_identity")
	private Long userIdentity;
}
package br.com.dfdevforge.sisfintransaction.transaction.model.objectiveitem.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.objective.entities.ObjectiveEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "obi_objective_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class ObjectiveItemEntity extends BaseEntity {
	@Id
	@Column(name = "obi_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "obi_description")
	private String description;

	@Column(name = "obi_sequential")
	private Integer sequential;

	@Column(name = "obi_unitary_value")
	private BigDecimal unitaryValue;

	@Column(name = "obi_amount")
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "obj_identity")
	private ObjectiveEntity objective;

	@ManyToOne
	@JoinColumn(name = "acc_identity_target")
	private AccountEntity accountTarget;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
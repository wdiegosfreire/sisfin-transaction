package br.com.dfdevforge.sisfintransaction.entities;

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

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "obi_objective_item")
@EqualsAndHashCode(callSuper=false, of={"identity"})
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
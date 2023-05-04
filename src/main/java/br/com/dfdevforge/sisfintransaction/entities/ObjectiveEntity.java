package br.com.dfdevforge.sisfintransaction.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "obj_objective")
@EqualsAndHashCode(callSuper=false, of={"identity"})
public class ObjectiveEntity extends BaseEntity {
	@Id
	@Column(name = "obj_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "obj_description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "loc_identity")
	private LocationEntity location;

	@ManyToOne
	@JoinColumn(name = "acc_identity_source")
	private AccountEntity accountSource;

	@ManyToOne
	@JoinColumn(name = "acc_identity_target")
	private AccountEntity accountTarget;

	@Transient
	private Set<ObjectiveMovementEntity> objectiveMovementList;

	@Transient
	private Set<ObjectiveItemEntity> objectiveItemList;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
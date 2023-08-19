package br.com.dfdevforge.sisfintransaction.entities;

import java.util.List;

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

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "obj_objective")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
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

	@Transient
	private List<ObjectiveMovementEntity> objectiveMovementList;

	@Transient
	private List<ObjectiveItemEntity> objectiveItemList;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
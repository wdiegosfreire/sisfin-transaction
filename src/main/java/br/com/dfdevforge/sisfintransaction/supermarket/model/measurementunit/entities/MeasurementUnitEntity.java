package br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "meu_measurement_unit")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class MeasurementUnitEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "meu_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "meu_name")
	private String name;

	@Column(name = "meu_acronym")
	private String acronym;

	@Column(name = "meu_divider")
	private Integer divider;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final MeasurementUnitEntityProps props = new MeasurementUnitEntityProps();

	@Transient
	@JsonIgnore
	public final MeasurementUnitEntityMethods methods = new MeasurementUnitEntityMethods(this);
}
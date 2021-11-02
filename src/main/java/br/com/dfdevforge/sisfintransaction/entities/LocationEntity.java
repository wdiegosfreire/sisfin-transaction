package br.com.dfdevforge.sisfintransaction.entities;

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
@Table(name = "loc_location")
@EqualsAndHashCode(callSuper=false, of={"identity"})
public class LocationEntity extends BaseEntity {
	@Id
	@Column(name = "loc_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "loc_name")
	private String name;

	@Column(name = "loc_note")
	private String note;

	@ManyToOne()
	@JoinColumn(name="usr_identity")
	private UserEntity user;
}
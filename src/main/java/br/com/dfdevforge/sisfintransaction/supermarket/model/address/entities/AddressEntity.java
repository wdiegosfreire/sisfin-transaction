package br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities;

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
@Table(name = "add_address")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class AddressEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "add_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "add_street")
	private String street;

	@Column(name = "add_number")
	private String number;

	@Column(name = "add_neighborhood")
	private String neighborhood;

	@Column(name = "add_zip_code")
	private String zipCode;

	@Column(name = "add_city")
	private String city;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final AddressEntityProps props = new AddressEntityProps();

	@Transient
	@JsonIgnore
	public final AddressEntityMethods methods = new AddressEntityMethods(this);
}
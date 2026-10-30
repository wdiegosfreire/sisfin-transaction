package br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.address.entities.AddressEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "com_company")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class CompanyEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "com_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "com_name")
	private String name;

	@Column(name = "com_fantasy_name")
	private String fantasyName;

	@Column(name = "com_tax_number")
	private String taxNumber;

	@Column(name = "com_is_inactive")
	private Boolean isInactive;

	@ManyToOne
	@JoinColumn(name = "add_identity")
	private AddressEntity address;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final CompanyEntityProps props = new CompanyEntityProps();

	@Transient
	public final CompanyEntityMethods methods = new CompanyEntityMethods(this);
}
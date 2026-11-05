package br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.company.entities.CompanyEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "pco_product_company")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class ProductCompanyEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "pco_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "pco_code")
	private String code;

	@Column(name = "pco_description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "com_identity")
	private CompanyEntity company;

	@ManyToOne
	@JoinColumn(name = "pro_identity")
	private ProductEntity product;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final ProductCompanyEntityProps props = new ProductCompanyEntityProps();

	@Transient
	@JsonIgnore
	public final ProductCompanyEntityMethods methods = new ProductCompanyEntityMethods(this);
}
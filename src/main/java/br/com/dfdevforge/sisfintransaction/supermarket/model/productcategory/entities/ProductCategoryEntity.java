package br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "prc_product_category")
public class ProductCategoryEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "prc_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "prc_name")
	private String name;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final ProductCategoryEntityProps props = new ProductCategoryEntityProps();

	@Transient
	@JsonIgnore
	public final ProductCategoryEntityMethods methods = new ProductCategoryEntityMethods(this);
}
package br.com.dfdevforge.sisfintransaction.supermarket.model.productinfo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities.ProductEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productproperty.entities.ProductPropertyEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "pri_product_info")
public class ProductInfoEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "pri_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "pri_order")
	private Integer order;

	@Column(name = "pri_description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "pro_identity")
	@JsonIgnoreProperties("productInfoList")
	private ProductEntity product;

	@ManyToOne
	@JoinColumn(name = "ppt_identity")
	private ProductPropertyEntity productProperty;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final ProductInfoEntityProps props = new ProductInfoEntityProps();

	@Transient
	@JsonIgnore
	public final ProductInfoEntityMethods methods = new ProductInfoEntityMethods(this);
}
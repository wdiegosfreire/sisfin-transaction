package br.com.dfdevforge.sisfintransaction.supermarket.model.product.entities;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities.BrandEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.measurementunit.entities.MeasurementUnitEntity;
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcategory.entities.ProductCategoryEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "pro_product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class ProductEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "pro_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "pro_barcode")
	private String barcode;

	@Column(name = "pro_item_per_barcode")
	private Integer itemPerBarcode;

	@Column(name = "pro_quantity_per_item")
	private Integer quantityPerItem;

	@Column(name = "pro_is_favorite")
	private Boolean isFavorite;

	@ManyToOne
	@JoinColumn(name = "bra_identity")
	private BrandEntity brand;

	@ManyToOne
	@JoinColumn(name = "prc_identity")
	private ProductCategoryEntity productCategory;

	@ManyToOne
	@JoinColumn(name = "meu_identity")
	private MeasurementUnitEntity measurementUnit;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final ProductEntityProps props = new ProductEntityProps();

	@Transient
	@JsonIgnore
	public final ProductEntityMethods methods = new ProductEntityMethods(this);
}
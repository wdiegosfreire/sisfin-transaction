package br.com.dfdevforge.sisfintransaction.supermarket.model.productpricehistory.entities;

import java.math.BigDecimal;
import java.util.Date;

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
import br.com.dfdevforge.sisfintransaction.supermarket.model.productcompany.entities.ProductCompanyEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "pph_product_price_history")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class ProductPriceHistoryEntity extends BaseEntity implements UserOwnedEntity {
	@Id
	@Column(name = "pph_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "pph_price")
	private BigDecimal price;

	@Column(name = "pph_purchase_date")
	private Date purchaseDate;

	@ManyToOne
	@JoinColumn(name = "pco_identity")
	private ProductCompanyEntity productCompany;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final ProductPriceHistoryEntityProps props = new ProductPriceHistoryEntityProps();

	@Transient
	@JsonIgnore
	public final ProductPriceHistoryEntityMethods methods = new ProductPriceHistoryEntityMethods(this);
}
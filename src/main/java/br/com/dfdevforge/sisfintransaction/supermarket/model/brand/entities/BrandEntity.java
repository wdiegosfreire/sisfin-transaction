package br.com.dfdevforge.sisfintransaction.supermarket.model.brand.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "bra_brand")
@EqualsAndHashCode(callSuper = false, of = {"identity"})
public class BrandEntity extends BaseEntity {
	@Id
	@Column(name = "bra_identity", length = 36, updatable = false, nullable = false)
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String identity;

	@Column(name = "bra_name")
	private String name;

	@Column(name = "bra_email")
	private String email;

	@Column(name = "bra_website")
	private String website;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@PrePersist
	@PreUpdate
	private void blankStringsToNull() {
		name = this.emptyToNull(name);
		email = this.emptyToNull(email);
		website = this.emptyToNull(website);
	}
}
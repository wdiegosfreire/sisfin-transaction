package br.com.dfdevforge.sisfintransaction.statement.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "sti_statement_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity", scope = StatementItemEntity.class)
public class StatementItemEntity extends BaseEntity {
	@Id
	@Column(name = "sti_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "sti_movement_date")
	private Date movementDate;

	@Column(name = "sti_description")
	private String description;

	@Column(name = "sti_document_number")
	private String documentNumber;

	@Column(name = "sti_operation_type")
	private String operationType;

	@Column(name = "sti_movement_value")
	private BigDecimal movementValue;

	@Column(name = "sti_is_exported")
	private Boolean isExported;

	@ManyToOne
	@JoinColumn(name = "sta_identity")
	private StatementEntity statement;

	@ManyToOne
	@Transient
	private AccountEntity accountSource;

	@ManyToOne
	@Transient
	private AccountEntity accountTarget;

	@ManyToOne
	@Transient
	private LocationEntity location;

	@ManyToOne
	@Transient
	private PaymentMethodEntity paymentMethod;

	@Transient
	private String descriptionNew;

	@Transient
	private Boolean isVisible;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	public final StatementItemEntityProps props = new StatementItemEntityProps();

	public boolean isIncoming() {
		return this.operationType.equalsIgnoreCase("C");
	}

	public boolean isOutcoming() {
		return this.operationType.equalsIgnoreCase("D");
	}
}
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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "sti_statement_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
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

	@Column(name = "usr_identity")
	private Long userIdentity;
}
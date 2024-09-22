package br.com.dfdevforge.sisfintransaction.statement.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "sta_statement")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class StatementEntity extends BaseEntity {
	@Id
	@Column(name = "sta_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "sta_year")
	private Integer year;

	@Column(name = "sta_month")
	private Integer month;

	@Column(name = "sta_opening_balance")
	private BigDecimal openingBalance;

	@Column(name = "sta_closing_balance")
	private BigDecimal closingBalance;

	@Column(name = "sta_is_closed")
	private Boolean isClosed;

	@ManyToOne
	@JoinColumn(name = "stt_identity")
	private StatementTypeEntity statementType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "statement")
	private List<StatementItemEntity> statementItemList;

	@Column(name = "usr_identity")
	private Long userIdentity;

	@Transient
	private String statementFile;

	@Transient
	private Boolean isCreateMovement;

	@Transient
	private StatementEntityProps props = new StatementEntityProps();
}
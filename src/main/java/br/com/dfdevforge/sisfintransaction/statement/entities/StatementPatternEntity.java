package br.com.dfdevforge.sisfintransaction.statement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.LocationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "stp_statement_pattern")
public class StatementPatternEntity extends BaseEntity {
	@Id
	@Column(name = "stp_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "stp_comparator")
	private String comparator;

	@Column(name = "stp_description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "acc_identity_target")
	private AccountEntity accountTarget;

	@ManyToOne
	@JoinColumn(name = "loc_identity")
	private LocationEntity location;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
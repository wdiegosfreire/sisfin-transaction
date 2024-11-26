package br.com.dfdevforge.sisfintransaction.statement.model.statementtype.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.statement.model.bank.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.transaction.model.account.entities.AccountEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"identity"})
@Entity
@Table(name = "stt_statement_type")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "identity")
public class StatementTypeEntity extends BaseEntity {
	@Id
	@Column(name = "stt_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "stt_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "ban_identity")
	private BankEntity bank;

	@ManyToOne
	@JoinColumn(name = "acc_identity_source")
	private AccountEntity accountSource;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
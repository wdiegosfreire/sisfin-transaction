package br.com.dfdevforge.sisfintransaction.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.dfdevforge.common.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "acc_account")
@EqualsAndHashCode(callSuper=false, of={"identity"})
public class AccountEntity extends BaseEntity {
	@Id
	@Column(name = "acc_identity")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long identity;

	@Column(name = "acc_name")
	private String name;

	@Column(name = "acc_level")
	private String level;

	@Column(name = "acc_note")
	private String note;

	@Column(name = "acc_is_inactive")
	private Boolean isInactive;

	@ManyToOne
	@JoinColumn(name = "acc_identity_parent")
	private AccountEntity accountParent;

	@Column(name = "usr_identity")
	private Long userIdentity;
}
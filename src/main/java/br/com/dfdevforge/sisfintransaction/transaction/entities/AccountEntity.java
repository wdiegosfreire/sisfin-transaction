package br.com.dfdevforge.sisfintransaction.transaction.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "acc_account")
public class AccountEntity extends BaseEntity {
	public AccountEntity(Long identity) {
		this.identity = identity;
	}

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

	@Column(name = "acc_icon")
	private String icon;

	@Column(name = "acc_is_inactive")
	private Boolean isInactive;

	@ManyToOne
	@JoinColumn(name = "acc_identity_parent")
	private AccountEntity accountParent;

	@Transient
	private List<AccountEntity> accountListChild;

	@Column(name = "usr_identity")
	private Long userIdentity;

	public boolean isLevelOne() {
		return this.level.length() == 3;
	}

	public boolean isLevelTwo() {
		return this.level.length() == 6;
	}

	public boolean isLevelThree() {
		return this.level.length() == 9;
	}
}
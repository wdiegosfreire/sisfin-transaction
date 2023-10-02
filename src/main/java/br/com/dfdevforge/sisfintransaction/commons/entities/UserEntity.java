package br.com.dfdevforge.sisfintransaction.commons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "usr_user")
@EqualsAndHashCode(callSuper = false, of = {"identity"})
public class UserEntity {
	@Id
	@Column(name = "usr_identity")
	private Long identity;

	@Column(name = "usr_name")
	private String name;

	@Column(name = "usr_password")
	private String password;

	@Column(name = "usr_email")
	private String email;
}
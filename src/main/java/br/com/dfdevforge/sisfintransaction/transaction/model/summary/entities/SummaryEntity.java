package br.com.dfdevforge.sisfintransaction.transaction.model.summary.entities;

import java.util.Date;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import br.com.dfdevforge.sisfintransaction.commons.security.UserOwnedEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryEntity extends BaseEntity implements UserOwnedEntity {
	private Date periodDate;
	private Integer periodRange;
	private Long userIdentity;
	
	private Long balanceAccountIdentity;
	private String outcomingAccountLevel;
}
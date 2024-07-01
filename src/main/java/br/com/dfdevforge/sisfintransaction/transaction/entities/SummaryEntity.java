package br.com.dfdevforge.sisfintransaction.transaction.entities;

import java.util.Date;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryEntity extends BaseEntity {
	private Date periodDate;
	private Long incomingOutcomingChartAccountIdentity;
	private Long userIdentity;
}
package br.com.dfdevforge.sisfintransaction.transaction.charts;

import java.util.ArrayList;
import java.util.List;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarChartData extends BaseEntity {
	private List<String> labels;
	private List<DataSet> datasets;

	public BarChartData() {
		this.labels = new ArrayList<>();
		this.datasets = new ArrayList<>();
	}

	private Long userIdentity;
}
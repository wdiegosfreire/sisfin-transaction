package br.com.dfdevforge.sisfintransaction.transaction.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PieChartData extends BaseEntity {
	private List<String> labels;
	private List<DataSet> datasets;
	private Long userIdentity;

	public PieChartData() {
		this.labels = new ArrayList<>();
		this.datasets = new ArrayList<>();
	}

	@Getter
	@Setter
	public class DataSet {
		private List<String> backgroundColor;
		private List<BigDecimal> data;

		public DataSet() {
			this.backgroundColor = new ArrayList<>();
			this.data = new ArrayList<>();
		}
	}
}
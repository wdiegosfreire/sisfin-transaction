package br.com.dfdevforge.sisfintransaction.transaction.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.dfdevforge.sisfintransaction.commons.entities.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineChartData extends BaseEntity {
	private List<String> labels;
	private List<DataSet> datasets;
	private Long userIdentity;

	public LineChartData() {
		this.labels = new ArrayList<>();
		this.datasets = new ArrayList<>();
	}

	@Getter
	@Setter
	public class DataSet {
		private String icon;
		private String label;
		private String identifier;
		private String backgroundColor;
		private List<BigDecimal> data;

		public DataSet() {
			this.data = new ArrayList<>();
		}
	}
}
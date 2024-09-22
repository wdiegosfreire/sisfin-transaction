package br.com.dfdevforge.sisfintransaction.transaction.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
package br.com.dfdevforge.sisfintransaction.transaction.services.summary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.enums.ColorEnum;
import br.com.dfdevforge.sisfintransaction.commons.enums.DatePatternEnum;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.charts.BarChartData;
import br.com.dfdevforge.sisfintransaction.transaction.charts.DataSet;
import br.com.dfdevforge.sisfintransaction.transaction.charts.LineChartData;
import br.com.dfdevforge.sisfintransaction.transaction.charts.PieChartData;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositoryCustomized;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositorySearchAllTypeOutcoming;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalByOutcomingAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalIncomingByBalanceAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalOutcomingByBalanceAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalPreviousByBalanceAccount;

@Service
@RequestScope
@Transactional
public class SummaryAccessModuleService extends SummaryBaseService implements CommonService {
	private static final String COL_ACC_NAME = "acc_name"; 
	private final AccountRepositoryCustomized accountRepositoryCustomized;
	private final AccountRepositorySearchAllTypeOutcoming accountRepositorySearchAllTypeOutcoming;
	private final SummaryRepositorySelectTotalByOutcomingAccount summaryRepositorySelectTotalByOutcomingAccount;
	private final SummaryRepositorySelectTotalIncomingByBalanceAccount summaryRepositorySelectTotalIncomingByBalanceAccount;
	private final SummaryRepositorySelectTotalPreviousByBalanceAccount summaryRepositorySelectTotalPreviousByBalanceAccount;
	private final SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount;


	@Autowired
	public SummaryAccessModuleService(AccountRepositoryCustomized accountRepositoryCustomized, AccountRepositorySearchAllTypeOutcoming accountRepositorySearchAllTypeOutcoming, SummaryRepositorySelectTotalByOutcomingAccount summaryRepositorySelectTotalByOutcomingAccount, SummaryRepositorySelectTotalIncomingByBalanceAccount summaryRepositorySelectTotalIncomingByBalanceAccount, SummaryRepositorySelectTotalPreviousByBalanceAccount summaryRepositorySelectTotalPreviousByBalanceAccount, SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount) {
		this.accountRepositoryCustomized = accountRepositoryCustomized;
		this.accountRepositorySearchAllTypeOutcoming = accountRepositorySearchAllTypeOutcoming;
		this.summaryRepositorySelectTotalByOutcomingAccount = summaryRepositorySelectTotalByOutcomingAccount;
		this.summaryRepositorySelectTotalIncomingByBalanceAccount = summaryRepositorySelectTotalIncomingByBalanceAccount;
		this.summaryRepositorySelectTotalPreviousByBalanceAccount = summaryRepositorySelectTotalPreviousByBalanceAccount;
		this.summaryRepositorySelectTotalOutcomingByBalanceAccount = summaryRepositorySelectTotalOutcomingByBalanceAccount;
	}

	private Map<String, List<Tuple>> totalOutcomingAccountMap;

	private List<String> periodList;
	private List<AccountEntity> accountListBalanceCombo;
	private List<AccountEntity> accountListOutcomingCombo;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBalanceAccounts();
		this.findOutcomingAccounts();
		this.setDefaultBalanceAccount();
		this.getPeriodList();
		this.getTotalValueGroupedByOutcomingAccount();
		this.buildIncomingOutcomingSummaryTableData();

		this.buildOutcomingSummaryLineChart();
		this.buildOutcomingSummaryPieChart();
	}

	private void findBalanceAccounts() {
		this.accountListBalanceCombo = this.accountRepositoryCustomized.searchAllTypeBalance(this.summaryParam.getUserIdentity());
		this.setArtifact("accountListBalanceCombo", this.accountListBalanceCombo);
	}

	private void findOutcomingAccounts() {
		this.accountListOutcomingCombo = this.accountRepositorySearchAllTypeOutcoming.execute(this.summaryParam.getUserIdentity());
		this.setArtifact("accountListOutcomingCombo", this.accountListOutcomingCombo);
	}

	private void setDefaultBalanceAccount() {
		if (this.summaryParam.getBalanceAccountIdentity() == null)
			this.summaryParam.setBalanceAccountIdentity(this.accountListBalanceCombo.get(0).getIdentity());
		if (this.summaryParam.getOutcomingAccountLevel() == null)
			this.summaryParam.setOutcomingAccountLevel(this.accountListOutcomingCombo.get(0).getLevel());
	}

	private void getPeriodList() {
		this.periodList = new ArrayList<>();

		for (int i = this.summaryParam.getPeriodRange() - 1; i >= 0; i --) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);
			periodList.add(Utils.date.toStringFromDate(period, DatePatternEnum.PT_BR_BARS_MES_ANO));
		}
	}

	private void getTotalValueGroupedByOutcomingAccount() {
		this.totalOutcomingAccountMap = new HashMap<>();

		this.periodList.forEach(periodString -> {

			Date periodDate = Utils.date.toDateFromString(periodString, DatePatternEnum.PT_BR_BARS_MES_ANO);

			List<Tuple> resultSet = this.summaryRepositorySelectTotalByOutcomingAccount.execute(periodDate, this.summaryParam.getOutcomingAccountLevel(), this.summaryParam.getUserIdentity());

			this.totalOutcomingAccountMap.put(periodString, resultSet);
		});
	}

	private void buildIncomingOutcomingSummaryTableData() {
		BarChartData incomingOutcomingSummaryTableData = new BarChartData();
		incomingOutcomingSummaryTableData.setUserIdentity(this.summaryParam.getUserIdentity());
		incomingOutcomingSummaryTableData.setLabels(this.periodList);

		DataSet dataSetPrevious = this.buildDataSet("Previous", "fa-arrow-rotate-left", "A", "orange");
		DataSet dataSetIncoming = this.buildDataSet("Incoming", "fa-arrow-trend-up", "B", "green");
		DataSet dataSetOutcoming = this.buildDataSet("Outcoming", "fa-arrow-trend-down", "C", "red");
		DataSet dataSetBalance = this.buildDataSet("Balance", "fa-hand-holding-dollar", "D", "blue");

		incomingOutcomingSummaryTableData.getDatasets().add(dataSetPrevious);
		incomingOutcomingSummaryTableData.getDatasets().add(dataSetIncoming);
		incomingOutcomingSummaryTableData.getDatasets().add(dataSetOutcoming);
		incomingOutcomingSummaryTableData.getDatasets().add(dataSetBalance);

		for (int i = this.summaryParam.getPeriodRange() - 1; i >= 0; i--) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);

			BigDecimal previous = this.summaryRepositorySelectTotalPreviousByBalanceAccount.execute(period, this.summaryParam.getBalanceAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal incoming = this.summaryRepositorySelectTotalIncomingByBalanceAccount.execute(period, this.summaryParam.getBalanceAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal outcoming = this.summaryRepositorySelectTotalOutcomingByBalanceAccount.execute(period, this.summaryParam.getBalanceAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal balance = previous.add(incoming).subtract(outcoming);

			dataSetPrevious.getData().add(previous);
			dataSetIncoming.getData().add(incoming);
			dataSetOutcoming.getData().add(outcoming);
			dataSetBalance.getData().add(balance);
		}

		this.setArtifact("incomingOutcomingSummaryTableData", incomingOutcomingSummaryTableData);
	}

	private void buildOutcomingSummaryLineChart() {
		LineChartData outcomingSummaryLineChart = new LineChartData();
		outcomingSummaryLineChart.setUserIdentity(this.summaryParam.getUserIdentity());
		outcomingSummaryLineChart.setLabels(this.periodList);

		ColorEnum[] colorEnum = ColorEnum.values();

		List<LineChartData.DataSet> dataSetList = new ArrayList<>();
		List<Tuple> resultSetAccounts = this.totalOutcomingAccountMap.get(this.periodList.get(0));

		for (int i = 0; i < resultSetAccounts.size(); i++) {
			Tuple tuple = resultSetAccounts.get(i);

			LineChartData.DataSet dataSet = new LineChartData().new DataSet();
			dataSet.setLabel(tuple.get(COL_ACC_NAME, String.class));
			dataSet.setBackgroundColor(colorEnum[i].getCode());
			dataSet.setData(new ArrayList<>());

			dataSetList.add(dataSet);
		}

		outcomingSummaryLineChart.getLabels().forEach(label -> {
			List<Tuple> resultSet = this.totalOutcomingAccountMap.get(label);
			resultSet.forEach(result -> {
				String itemName = result.get(COL_ACC_NAME, String.class);
				
				LineChartData.DataSet dataSetTemp = dataSetList.stream().filter(dataSet -> dataSet.getLabel().equals(itemName)).findFirst().get();
				dataSetTemp.getData().add(result.get("obi_total_value", BigDecimal.class));
			});
		});

		outcomingSummaryLineChart.setDatasets(dataSetList);

		this.setArtifact("outcomingSummaryLineChart", outcomingSummaryLineChart);
	}

	private void buildOutcomingSummaryPieChart() {
		Map<String, PieChartData> test = new HashMap<>();

		this.periodList.forEach(periodString -> {
			PieChartData outcomingSummaryPieChart = new PieChartData();
			outcomingSummaryPieChart.setUserIdentity(this.summaryParam.getUserIdentity());

			ColorEnum[] colorEnum = ColorEnum.values();

			List<Tuple> result = this.totalOutcomingAccountMap.get(periodString);
			br.com.dfdevforge.sisfintransaction.transaction.charts.PieChartData.DataSet dataSet = new PieChartData().new DataSet();
			for (int i = 0; i < result.size(); i++) {
				Tuple tuple = result.get(i);

				outcomingSummaryPieChart.getLabels().add(tuple.get(COL_ACC_NAME, String.class));

				dataSet.getData().add(tuple.get("obi_total_value", BigDecimal.class));
				dataSet.getBackgroundColor().add(colorEnum[i].getCode());

			}
			outcomingSummaryPieChart.getDatasets().add(dataSet);

			test.put(periodString, outcomingSummaryPieChart);
		});

		this.setArtifact("outcomingSummaryPieChart", test.entrySet().stream().sorted(Map.Entry.<String, PieChartData>comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new)));
	}

	private DataSet buildDataSet(String label, String icon, String identifier, String color) {
		DataSet dataSet = new DataSet();

		dataSet.setLabel(label);
		dataSet.setIcon(icon);
		dataSet.setIdentifier(identifier);
		dataSet.setBackgroundColor(color);

		return dataSet;
	}
}
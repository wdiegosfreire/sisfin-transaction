package br.com.dfdevforge.sisfintransaction.transaction.services.summary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import br.com.dfdevforge.sisfintransaction.commons.enums.DatePatternEnum;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.transaction.charts.BarChartData;
import br.com.dfdevforge.sisfintransaction.transaction.charts.DataSet;
import br.com.dfdevforge.sisfintransaction.transaction.entities.AccountEntity;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.account.AccountRepositoryCustomized;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalIncomingByBalanceAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalOutcomingByBalanceAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalPreviousByBalanceAccount;

@Service
@RequestScope
@Transactional
public class SummaryAccessModuleService extends SummaryBaseService implements CommonService {
	private final AccountRepositoryCustomized accountRepositoryCustomized;

	private final SummaryRepositorySelectTotalIncomingByBalanceAccount summaryRepositorySelectTotalIncomingByBalanceAccount;
	private final SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount;
	private final SummaryRepositorySelectTotalPreviousByBalanceAccount summaryRepositorySelectTotalPreviousByBalanceAccount;

	@Autowired
	public SummaryAccessModuleService(AccountRepositoryCustomized accountRepositoryCustomized, SummaryRepositorySelectTotalIncomingByBalanceAccount summaryRepositorySelectTotalIncomingByBalanceAccount, SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount, SummaryRepositorySelectTotalPreviousByBalanceAccount summaryRepositorySelectTotalPreviousByBalanceAccount) {
		this.accountRepositoryCustomized = accountRepositoryCustomized;
		this.summaryRepositorySelectTotalIncomingByBalanceAccount = summaryRepositorySelectTotalIncomingByBalanceAccount;
		this.summaryRepositorySelectTotalOutcomingByBalanceAccount = summaryRepositorySelectTotalOutcomingByBalanceAccount;
		this.summaryRepositorySelectTotalPreviousByBalanceAccount  = summaryRepositorySelectTotalPreviousByBalanceAccount;
	}

	private List<AccountEntity> accountListBalanceCombo;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBalanceAccounts();
		this.setDefaultBalanceAccount();
		this.getSummaryPanelData();
	}

	private void findBalanceAccounts() {
		this.accountListBalanceCombo = this.accountRepositoryCustomized.searchAllTypeBalance(this.summaryParam.getUserIdentity());
		this.setArtifact("accountListBalanceCombo", this.accountListBalanceCombo);
	}

	private void setDefaultBalanceAccount() {
		if (this.summaryParam.getIncomingOutcomingChartAccountIdentity() == null)
			this.summaryParam.setIncomingOutcomingChartAccountIdentity(this.accountListBalanceCombo.get(0).getIdentity());
	}

	private void getSummaryPanelData() {
		BarChartData barChartData = new BarChartData();
		barChartData.setUserIdentity(this.summaryParam.getUserIdentity());

		for (int i = this.summaryParam.getIncomingOutcomingChartPeriodRangeValue() - 1; i >= 0; i --) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);
			barChartData.getLabels().add(Utils.date.toStringFromDate(period, DatePatternEnum.PT_BR_BARS_MES_ANO));
		}

		DataSet dataSetPrevious = new DataSet();
		dataSetPrevious.setLabel("Previous");
		dataSetPrevious.setIcon("fa-arrow-rotate-left");
		dataSetPrevious.setIdentifier("A");
		dataSetPrevious.setBackgroundColor("orange");

		DataSet dataSetIncoming = new DataSet();
		dataSetIncoming.setLabel("Incoming");
		dataSetIncoming.setIcon("fa-arrow-trend-up");
		dataSetIncoming.setIdentifier("B");
		dataSetIncoming.setBackgroundColor("green");

		DataSet dataSetOutcoming = new DataSet();
		dataSetOutcoming.setLabel("Outcoming");
		dataSetOutcoming.setIcon("fa-arrow-trend-down");
		dataSetOutcoming.setIdentifier("C");
		dataSetOutcoming.setBackgroundColor("red");

		DataSet dataSetBalance = new DataSet();
		dataSetBalance.setLabel("Balance");
		dataSetBalance.setIcon("fa-hand-holding-dollar");
		dataSetBalance.setIdentifier("D");
		dataSetBalance.setBackgroundColor("blue");

		barChartData.getDatasets().add(dataSetPrevious);
		barChartData.getDatasets().add(dataSetIncoming);
		barChartData.getDatasets().add(dataSetOutcoming);
		barChartData.getDatasets().add(dataSetBalance);

		for (int i = this.summaryParam.getIncomingOutcomingChartPeriodRangeValue() - 1; i >= 0; i--) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);

			BigDecimal previous = this.summaryRepositorySelectTotalPreviousByBalanceAccount.execute(period, this.summaryParam.getIncomingOutcomingChartAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal incoming = this.summaryRepositorySelectTotalIncomingByBalanceAccount.execute(period, this.summaryParam.getIncomingOutcomingChartAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal outcoming = this.summaryRepositorySelectTotalOutcomingByBalanceAccount.execute(period, this.summaryParam.getIncomingOutcomingChartAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal balance = previous.add(incoming).subtract(outcoming);

			dataSetPrevious.getData().add(previous);
			dataSetIncoming.getData().add(incoming);
			dataSetOutcoming.getData().add(outcoming);
			dataSetBalance.getData().add(balance);
		}

		this.setArtifact("barChartData", barChartData);
	}
}
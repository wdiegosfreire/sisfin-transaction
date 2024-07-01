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
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalIncommingByBalanceAccount;
import br.com.dfdevforge.sisfintransaction.transaction.repositories.summary.SummaryRepositorySelectTotalOutcomingByBalanceAccount;

@Service
@RequestScope
@Transactional
public class SummaryAccessModuleService extends SummaryBaseService implements CommonService {
	private final AccountRepositoryCustomized accountRepositoryCustomized;
	private final SummaryRepositorySelectTotalIncommingByBalanceAccount summaryRepositorySelectTotalIncommingByBalanceAccount;
	private final SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount;

	@Autowired
	public SummaryAccessModuleService(AccountRepositoryCustomized accountRepositoryCustomized, SummaryRepositorySelectTotalIncommingByBalanceAccount summaryRepositorySelectTotalIncommingByBalanceAccount, SummaryRepositorySelectTotalOutcomingByBalanceAccount summaryRepositorySelectTotalOutcomingByBalanceAccount) {
		this.accountRepositoryCustomized = accountRepositoryCustomized;
		this.summaryRepositorySelectTotalIncommingByBalanceAccount = summaryRepositorySelectTotalIncommingByBalanceAccount;
		this.summaryRepositorySelectTotalOutcomingByBalanceAccount = summaryRepositorySelectTotalOutcomingByBalanceAccount;
	}

	private List<AccountEntity> accountListBalanceCombo;

	@Override
	public void executeBusinessRule() throws BaseException {
		this.findBalanceAccounts();
		this.setDefaultBalanceAccount();
		this.getIncomingOutcomingChartData();
	}

	private void findBalanceAccounts() {
		this.accountListBalanceCombo = this.accountRepositoryCustomized.searchAllTypeBalance(this.summaryParam.getUserIdentity());
		this.setArtifact("accountListBalanceCombo", this.accountListBalanceCombo);
	}

	private void setDefaultBalanceAccount() {
		if (this.summaryParam.getIncomingOutcomingChartAccountIdentity() == null)
			this.summaryParam.setIncomingOutcomingChartAccountIdentity(this.accountListBalanceCombo.get(0).getIdentity());
	}

	private void getIncomingOutcomingChartData() {
		BarChartData barChartData = new BarChartData();
		barChartData.setUserIdentity(this.summaryParam.getUserIdentity());

		for (int i = 5; i >= 0; i --) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);
			barChartData.getLabels().add(Utils.date.toStringFromDate(period, DatePatternEnum.PT_BR_BARS_MES_ANO));
		}

		DataSet dataSetIncoming = new DataSet();
		dataSetIncoming.setLabel("Incoming");
		dataSetIncoming.setBackgroundColor("green");

		DataSet dataSetOutcoming = new DataSet();
		dataSetOutcoming.setLabel("Outcoming");
		dataSetOutcoming.setBackgroundColor("red");

		DataSet dataSetBalance = new DataSet();
		dataSetBalance.setLabel("Balance");
		dataSetBalance.setBackgroundColor("blue");

		barChartData.getDatasets().add(dataSetIncoming);
		barChartData.getDatasets().add(dataSetOutcoming);
		barChartData.getDatasets().add(dataSetBalance);

		for (int i = 5; i >= 0; i --) {
			Date period = Utils.date.minusMonths(this.summaryParam.getPeriodDate(), i);

			BigDecimal incoming = this.summaryRepositorySelectTotalIncommingByBalanceAccount.execute(period, this.summaryParam.getIncomingOutcomingChartAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal outcoming = this.summaryRepositorySelectTotalOutcomingByBalanceAccount.execute(period, this.summaryParam.getIncomingOutcomingChartAccountIdentity(), this.summaryParam.getUserIdentity());
			BigDecimal balance = incoming.subtract(outcoming);

			dataSetIncoming.getData().add(incoming);
			dataSetOutcoming.getData().add(outcoming);
			dataSetBalance.getData().add(balance);
		}

		this.setArtifact("barChartData", barChartData);
	}
}


















package br.com.dfdevforge.sisfintransaction.statement.service.statement;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import br.com.dfdevforge.sisfintransaction.commons.exceptions.BaseException;
import br.com.dfdevforge.sisfintransaction.commons.exceptions.DebugException;
import br.com.dfdevforge.sisfintransaction.commons.services.CommonService;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import br.com.dfdevforge.sisfintransaction.statement.entities.BankEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementItemEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementPatternEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.StatementTypeEntity;
import br.com.dfdevforge.sisfintransaction.statement.entities.YamlStatementEntity;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementPatternRepository;
import br.com.dfdevforge.sisfintransaction.statement.repositories.StatementRepository;
import br.com.dfdevforge.sisfintransaction.statement.service.statementitem.StatementItemExecuteRegistrationService;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveItemEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.ObjectiveMovementEntity;
import br.com.dfdevforge.sisfintransaction.transaction.entities.PaymentMethodEntity;
import br.com.dfdevforge.sisfintransaction.transaction.services.objective.ObjectiveExecuteRegistrationService;

@Service
@RequestScope
@Transactional
public class StatementExecuteRegistrationService extends StatementBaseService implements CommonService {
	private static final String EXECECAO_NAO_IDENTIFICADA = "Exceção não Identificada";
	private static final String EXTRATO_CONTA_CORRENTE = "Extrato de conta corrente";
	private static final String FATURA_CARTAO_CREDITO = "Fatura do Cartão de Crédito";
	private static final String SALDO_ANTERIOR = "Saldo Anterior";
	private static final String DD_MM_YYYY = "dd/MM/yyyy";
	private static final String POUPANCA = "Poupança";

	private final StatementRepository statementRepository;
	private final StatementPatternRepository statementPatternRepository;
	private final ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService;
	private final StatementItemExecuteRegistrationService statementItemExecuteRegistrationService;

	@Autowired
	public StatementExecuteRegistrationService(StatementRepository statementRepository, StatementPatternRepository statementPatternRepository, ObjectiveExecuteRegistrationService objectiveExecuteRegistrationService, StatementItemExecuteRegistrationService statementItemExecuteRegistrationService) {
		this.statementRepository = statementRepository;
		this.statementPatternRepository = statementPatternRepository;
		this.objectiveExecuteRegistrationService = objectiveExecuteRegistrationService;
		this.statementItemExecuteRegistrationService = statementItemExecuteRegistrationService;
	}

	private byte[] statementByteArray;
	private String statementExtension;
	private List<String> fileContentList = new ArrayList<>();

	private List<StatementPatternEntity> statementPatternList;

	private StatementEntity statement = new StatementEntity();

	@SuppressWarnings("unused")
	private String statementBank;
	private String statementType;

	@Override
	public void executeBusinessRule() throws BaseException {
		try {
			this.getFileByteArrayFromBase64();
			this.getFileExtensionFromBase64();
			this.createBufferedReaderFromFileByteArray();
			this.importDataFromStatementFile();
			this.getStatementPatterns();
			this.exportIdentifiableStatementItems();
		}
		catch (ParseException | IOException e) {
			Utils.log.stackTrace(e);
			throw new DebugException(e.getClass().toString(), Arrays.toString(e.getStackTrace()));
		}
	}

	@Override
	public Map<String, Object> returnBusinessData() {
		this.setArtifact("statementRegistered", this.statementParam);
		return super.returnBusinessData();
	}

	private void getFileByteArrayFromBase64() {
		this.statementByteArray = Utils.decrypt.fromBase64(this.statementParam.getStatementFile().split("base64,")[1]);
	}

	private void getFileExtensionFromBase64() {
		this.statementExtension = ".txt";
		if (this.statementParam.getStatementFile().indexOf("application/octet-stream") != -1)
			this.statementExtension = ".yaml";
	}

	private void createBufferedReaderFromFileByteArray() throws IOException {
		BufferedReader bufferedReader = null;
		InputStream inputStream = null;

		inputStream = new ByteArrayInputStream(this.statementByteArray);
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));

		String line = bufferedReader.readLine();
		while (line != null) {
			this.fileContentList.add(line);
			line = bufferedReader.readLine();
		}

		inputStream.close();
		bufferedReader.close();
	}

	private void importDataFromStatementFile() throws ParseException, BaseException, IOException {
		if (this.statementExtension.equalsIgnoreCase(".txt")) {
			this.invocarRegraImportarFormatoTxt();
		}
		else if (this.statementExtension.equalsIgnoreCase(".csv")) {
//			new NgcExtratoImportarFormatoCsv(this.statement, this.connectionManager).execute()
		}
		else if (this.statementExtension.equalsIgnoreCase(".yaml")) {
			this.invocarRegraImportarFormatoYaml();
		}
	}

	private void getStatementPatterns() {
		this.statementPatternList = statementPatternRepository.findByUserIdentityOrderByComparatorAsc(this.statementParam.getUserIdentity());
	}

	private void exportIdentifiableStatementItems() throws BaseException {
		for (StatementItemEntity statementItem : this.statement.getStatementItemList()) {
			StatementPatternEntity statementPattern = this.statementPatternList.stream().filter(x -> statementItem.getDescription().contains(x.getComparator())).findFirst().orElse(null);

			if (!Objects.isNull(statementPattern)) {
				PaymentMethodEntity paymentMethodDefault = new PaymentMethodEntity();
				paymentMethodDefault.setIdentity(2L);

				this.createAndSaveObjectiveFromStatement(statementItem, statementPattern, paymentMethodDefault);
				this.updateStatementItemToExported(statementItem);
			}
		}
	}

	/*
	 * Todos os metodos a partir deste ponto devem ser refatorados para uma classe especifica.
	 */
	private void createAndSaveObjectiveFromStatement(StatementItemEntity statementItem, StatementPatternEntity statementPattern, PaymentMethodEntity paymentMethod) throws BaseException {
		ObjectiveEntity objective = new ObjectiveEntity();
		objective.setObjectiveMovementList(new ArrayList<>());
		objective.setObjectiveItemList(new ArrayList<>());
		objective.setDescription(statementPattern.getDescription());
		objective.setLocation(statementPattern.getLocation());
		objective.setUserIdentity(this.statementParam.getUserIdentity());

		ObjectiveMovementEntity objectiveMovement = new ObjectiveMovementEntity();
		objectiveMovement.setDueDate(statementItem.getMovementDate());
		objectiveMovement.setPaymentDate(statementItem.getMovementDate());
		objectiveMovement.setValue(statementItem.getMovementValue());
		objectiveMovement.setInstallment(1);
		objectiveMovement.setPaymentMethod(paymentMethod);
		objectiveMovement.setAccountSource(this.statement.getStatementType().getAccountSource());

		ObjectiveItemEntity objectiveItem = new ObjectiveItemEntity();
		objectiveItem.setDescription(statementPattern.getDescription());
		objectiveItem.setSequential(1);
		objectiveItem.setUnitaryValue(statementItem.getMovementValue());
		objectiveItem.setAmount(new BigDecimal(1));
		objectiveItem.setAccountTarget(statementPattern.getAccountTarget());

		objective.getObjectiveMovementList().add(objectiveMovement);
		objective.getObjectiveItemList().add(objectiveItem);

		this.objectiveExecuteRegistrationService.setParams(objective, token);
		this.objectiveExecuteRegistrationService.execute();
	}

	private void updateStatementItemToExported(StatementItemEntity statementItem) throws BaseException {
		statementItem.setIsExported(Boolean.TRUE);

		this.statementItemExecuteRegistrationService.setParams(statementItem, token);
		this.statementItemExecuteRegistrationService.execute();
	}

	private void invocarRegraImportarFormatoTxt() throws ParseException, BaseException {
		this.statement.setIsClosed(Boolean.FALSE);
		this.statement.setUserIdentity(this.statementParam.getUserIdentity());
		this.statement.setStatementType(new StatementTypeEntity());
		this.statement.getStatementType().setBank(new BankEntity());

		/* Regra:
		 * Executar o metodo responsavel por identificar as informacoes do cabecalho do extrato
		 */
		this.identificarCabecalhoDoExtrato(this.statement);

		if (this.statementType.equals(EXTRATO_CONTA_CORRENTE)) {
			this.importarContaCorrente(this.statement);
		}
		else if (this.statementType.equals(POUPANCA)) {
			try {
				this.importarPoupancaModelOne(this.statement);
			}
			catch (Exception e) {
				this.importarPoupancaModelTwo(this.statement);
			}
		}
		else if (this.statementType.equals(FATURA_CARTAO_CREDITO)) {
			this.importarExtratoDoCartaoDeCredito(this.statement);
		}

		/*
		 * Inserindo os registros da tabela STA_STATEMENT
		 */
		this.statementRepository.save(this.statement);

		/*
		 * Inserindo os registros da tabela ITE_ITEM_EXTRATO
		 */
		for (StatementItemEntity statementItemLoop : this.statement.getStatementItemList()) {
			statementItemLoop.setStatement(this.statement);
			statementItemLoop.setUserIdentity(this.statement.getUserIdentity());
			statementItemLoop.setIsExported(Boolean.FALSE);

			this.statementItemExecuteRegistrationService.setParams(statementItemLoop, token);
			this.statementItemExecuteRegistrationService.execute();
		}
	}

	private void invocarRegraImportarFormatoYaml() throws IOException, BaseException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		YamlStatementEntity yamlStatementEntity = mapper.readValue(this.statementByteArray, YamlStatementEntity.class);

		this.statement.setYear(yamlStatementEntity.getYear());
		this.statement.setMonth(yamlStatementEntity.getMonth());
		this.statement.setIsClosed(Boolean.FALSE);
		this.statement.setStatementType(this.statementParam.getStatementType());
		this.statement.setOpeningBalance(yamlStatementEntity.getOpeningBalanceInBigDecimal());
		this.statement.setClosingBalance(yamlStatementEntity.getClosingBalanceInBigDecimal());
		this.statement.setStatementItemList(new ArrayList<>());
		this.statement.setUserIdentity(this.statementParam.getUserIdentity());

		// Identificar saldo anterior (positivo ou negativo) e gerar o primeiro movimento da competencia atual
		this.statement.getStatementItemList().add(StatementItemEntity.builder()
			.movementDate(Utils.date.getFirstDayOfMonth(yamlStatementEntity.getItemList().get(0).getDateObject()))
			.description("Previous Balance")
			.documentNumber("0")
			.movementValue(yamlStatementEntity.getOpeningBalanceInBigDecimal().abs())
			.operationType(yamlStatementEntity.getOpeningBalanceInBigDecimal().compareTo(new BigDecimal(0)) >= 0 ? "C" : "D")
			.isExported(Boolean.FALSE)
			.statement(this.statement)
			.userIdentity(this.statementParam.getUserIdentity())
			.build()
		);

		yamlStatementEntity.getItemList().forEach(item -> {
			StatementItemEntity statementItemTemp = StatementItemEntity.builder()
				.movementDate(item.getDateObject())
				.description(item.getDescription())
				.documentNumber("0")
				.movementValue(item.getValueInBigDecimal().abs())
				.operationType(item.getValueInBigDecimal().compareTo(new BigDecimal(0)) >= 0 ? "C" : "D")
				.isExported(Boolean.FALSE)
				.statement(this.statement)
				.userIdentity(this.statementParam.getUserIdentity())
				.build()
			;

			this.statement.getStatementItemList().add(statementItemTemp);
		});

		this.statementRepository.save(this.statement);
		for (StatementItemEntity statementItemLoop : this.statement.getStatementItemList()) {
			this.statementItemExecuteRegistrationService.setParams(statementItemLoop, token);
			this.statementItemExecuteRegistrationService.execute();
		}
	}

	private void identificarCabecalhoDoExtrato(StatementEntity statement) throws ParseException, BaseException {
		/* Regra:
		 * Identificar o banco
		 */
		boolean bankTypeFound = Boolean.FALSE;
		for (String fileLine : this.fileContentList) {
			// Detalhe: Banco do Brasil
			if (fileLine.indexOf("Banco do Brasil") != -1) {
				this.statementBank = "Banco do Brasil";
				bankTypeFound = Boolean.TRUE;
				break;
			}
		}

		if (!bankTypeFound)
			throw new BaseException(EXECECAO_NAO_IDENTIFICADA, "Create a BankTypeNotFoundException");

		/* Regra:
		 * Identificar o tipo de extrato
		 */
		boolean bankStatementFound = Boolean.FALSE;
		if (statementParam.getStatementType().getIdentity() != null) {
			statement.getStatementType().setIdentity(statementParam.getStatementType().getIdentity());
			statement.getStatementType().setAccountSource(statementParam.getStatementType().getAccountSource());

			bankStatementFound = Boolean.TRUE;
		}

		for (String fileLine : this.fileContentList) {
			// Detalhe: conta corrente
			if (fileLine.indexOf(EXTRATO_CONTA_CORRENTE) != -1) {
				this.statementType = EXTRATO_CONTA_CORRENTE;
				break;
			}

			// Detalhe: poupança
			else if (fileLine.indexOf("Variação:") != -1) {
				this.statementType = POUPANCA;
				break;
			}

			// Detalhe: ourocard
			else if (fileLine.indexOf(FATURA_CARTAO_CREDITO) != -1) {
				this.statementType = FATURA_CARTAO_CREDITO;
				break;
			}
		}

		if (!bankStatementFound)
			throw new BaseException(EXECECAO_NAO_IDENTIFICADA, "Create a BankStatementNotFoundException");

		/* Regra:
		 * Identificar a competencia
		 */
		boolean periodStatementFound = Boolean.FALSE;

		// Detalhe: se o tipo do extrato for a conta corrente
		if (this.statementType.equals(EXTRATO_CONTA_CORRENTE)) {
			for (String fileLine : this.fileContentList) {
				if (fileLine.toUpperCase().indexOf(SALDO_ANTERIOR.toUpperCase()) != -1) {
					// Detalhe: criar um Calendar para executar as operaes com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribu-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
					c.setTime(dateFormat.parse(fileLine.substring(0, 8)));

					// Detalhe: acrescentar um ms  data
					c.add(Calendar.MONTH, 1);

					// Detalhe: setar os valores de mes e ano ao statement
					statement.setMonth(c.get(Calendar.MONTH) + 1);
					statement.setYear(c.get(Calendar.YEAR));

					periodStatementFound = Boolean.TRUE;

					break;
				}
			}
		}

		// Detalhe: se o tipo do extrato for a poupança
		else if (this.statementType.equals(POUPANCA)) {
			for (String fileLine : this.fileContentList) {
				if (fileLine.toUpperCase().indexOf(SALDO_ANTERIOR.toUpperCase()) != -1) {
					// Detalhe: criar um Calendar para executar as operaes com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribu-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
					c.setTime(dateFormat.parse(fileLine.substring(0, 10)));

					// Detalhe: acrescentar um ms  data
					c.add(Calendar.DAY_OF_MONTH, 1);
					
					// Detalhe: setar os valores de mes e ano ao statement
					statement.setMonth(c.get(Calendar.MONTH) + 1);
					statement.setYear(c.get(Calendar.YEAR));
					
					periodStatementFound = Boolean.TRUE;
					
					break;
				}
			}
		}

		// Detalhe: se o tipo do extrato for a fatura ourocard
		else if (this.statementType.equals(FATURA_CARTAO_CREDITO)) {
			for (String fileLine : this.fileContentList) {
				if (fileLine.indexOf("Vencimento") != -1) {
					// Detalhe: criar um Calendar para executar as operaes com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribu-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					c.setTime(dateFormat.parse(fileLine.substring(18, 28)));

					// Detalhe: setar os valores de mes e ano ao statement
					statement.setMonth(c.get(Calendar.MONTH) + 1);
					statement.setYear(c.get(Calendar.YEAR));

					periodStatementFound = Boolean.TRUE;
					
					break;
				}
			}
		}

		if (!periodStatementFound)
			throw new BaseException(EXECECAO_NAO_IDENTIFICADA, "Create a PeriodStatementNotFoundException");
	}

	private void importarContaCorrente(StatementEntity statement) throws ParseException, ArrayIndexOutOfBoundsException
	{
		int index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (this.fileContentList.get(index).indexOf(SALDO_ANTERIOR) == -1)
			index++;

		// Detalhe: identificar o valor do saldo anterior
		String openingBalanceAux = this.fileContentList.get(index).substring(64, 78).trim();
		openingBalanceAux = openingBalanceAux.replace(".", "");
		openingBalanceAux = openingBalanceAux.replace(",", ".");
		statement.setOpeningBalance(new BigDecimal(openingBalanceAux));

		StatementItemEntity statementItem = null;
		statement.setStatementItemList(new ArrayList<>());

		while (this.fileContentList.get(index).indexOf("S A L D O") == -1) {
			if (!this.fileContentList.get(index).substring(0, 4).equals("    ")) {
				statementItem = new StatementItemEntity();
				DateFormat df = new SimpleDateFormat("dd/MM/yy");

				statementItem.setMovementDate(df.parse(this.fileContentList.get(index).substring(0, 8)));
				statementItem.setDescription(this.fileContentList.get(index).substring(17, 43).trim());
				statementItem.setDocumentNumber(this.fileContentList.get(index).substring(43, 64).trim());

				String aux = this.fileContentList.get(index).substring(64, 78).trim();
				aux = aux.replace(".", "");
				aux = aux.replace(",", ".");

				statementItem.setMovementValue(new BigDecimal(aux));
				statementItem.setOperationType(this.fileContentList.get(index).substring(79));

				statement.getStatementItemList().add(statementItem);
			}
			else {
				statementItem.setDescription(statementItem.getDescription() + " -> " + this.fileContentList.get(index).substring(17).trim());
			}

			index++;
		}

		// Identifica a linha que contm o saldo final e atribui  varivel apropriada
		String aux = this.fileContentList.get(index).substring(64, 78).trim();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		statement.setClosingBalance(new BigDecimal(aux));
		
		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento  sempre do ultimo dia do mes anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do mes de competencia.
		 */
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(statement.getStatementItemList().get(0).getMovementDate());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		statement.getStatementItemList().get(0).setMovementDate(calendar.getTime());
	}

	private void importarPoupancaModelOne(StatementEntity statement) throws ParseException
	{
		int index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (this.fileContentList.get(index).indexOf(SALDO_ANTERIOR) == -1)
			index++;

		/*
		 * Incio do processo de captura de dados
		 */
		String openingBalanceAux = this.fileContentList.get(index).substring(73, 85).trim();
		openingBalanceAux = openingBalanceAux.replaceAll("\\.", "");
		openingBalanceAux = openingBalanceAux.replace(",", ".");
		statement.setOpeningBalance(new BigDecimal(openingBalanceAux));

		StatementItemEntity statementItem = null;
		statement.setStatementItemList(new ArrayList<>());

		while (this.fileContentList.get(index) != null && this.fileContentList.get(index).indexOf("S A L D O") == -1)
		{
			if (!this.fileContentList.get(index).equals("") && !this.fileContentList.get(index).substring(0, 1).equals("-"))
			{
				statementItem = new StatementItemEntity();

				DateFormat df = new SimpleDateFormat(DD_MM_YYYY);

				statementItem.setMovementDate(df.parse(this.fileContentList.get(index).substring(0, 10)));

				statementItem.setDescription("Poupança: " + this.fileContentList.get(index).substring(20, 49).trim());

				String aux = this.fileContentList.get(index).substring(73, 85).trim();
				aux = aux.replace(".", "");
				aux = aux.replace(",", ".");

				statementItem.setMovementValue(new BigDecimal(aux));
				statementItem.setOperationType(this.fileContentList.get(index).substring(86, 88).trim());

				statement.getStatementItemList().add(statementItem);
			}

			index++;
		}

		// Identifica a linha que contm o saldo final e atribui  varivel apropriada
		String aux = this.fileContentList.get(index).substring(86, 98).trim();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		statement.setClosingBalance(new BigDecimal(aux));

		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento  sempre do ltimo dia do ms anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do ms de competncia.
		 */
		Calendar c = new GregorianCalendar();
		c.setTime(statement.getStatementItemList().get(0).getMovementDate());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		statement.getStatementItemList().get(0).setMovementDate(c.getTime());
	}

	private void importarPoupancaModelTwo(StatementEntity statement) throws ParseException
	{
		int index = 0;
		
		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (this.fileContentList.get(index).toUpperCase().indexOf(SALDO_ANTERIOR.toUpperCase()) == -1)
			index++;

		/*
		 * Incio do processo de captura de dados
		 */
		String openingBalanceAux = this.fileContentList.get(index).substring(76, 88).trim();
		openingBalanceAux = openingBalanceAux.replaceAll("\\.", "");
		openingBalanceAux = openingBalanceAux.replace(",", ".");
		statement.setOpeningBalance(new BigDecimal(openingBalanceAux));
		
		StatementItemEntity statementItem = null;
		statement.setStatementItemList(new ArrayList<>());

		BigDecimal saldoCompare = new BigDecimal(0);
		while (index < this.fileContentList.size())
		{
			if (!this.fileContentList.get(index).equals("") && !this.fileContentList.get(index).substring(0, 1).equals("-")) {
				statementItem = new StatementItemEntity();

				DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);

				statementItem.setMovementDate(dateFormat.parse(this.fileContentList.get(index).substring(0, 10)));

				statementItem.setDescription("Poupança: " + this.fileContentList.get(index).substring(20, 49).trim());

				// Recuperando o valor do movimento
				String valorAux = this.fileContentList.get(index).substring(76, 88).trim();
				valorAux = valorAux.replace(".", "");
				valorAux = valorAux.replace(",", ".");
				statementItem.setMovementValue(new BigDecimal(valorAux));
				statementItem.setDocumentNumber("0");

				// Recuperando o valor do saldo atual da linha para identificar se o movimento  crdito ou dbito
				String saldoLinha = this.fileContentList.get(index).substring(89, 102).trim();
				saldoLinha = saldoLinha.replace(".", "");
				saldoLinha = saldoLinha.replace(",", ".");

				BigDecimal saldoLinhaBig = new BigDecimal(saldoLinha);

				if (saldoCompare.equals(new BigDecimal(0)) || saldoCompare.compareTo(saldoLinhaBig) < 0)
					statementItem.setOperationType("C");
				else
					statementItem.setOperationType("D");

				saldoCompare = saldoLinhaBig;

				statement.getStatementItemList().add(statementItem);
			}

			index++;
		}
		
		// Identifica a linha que contm o saldo final e atribui  varivel apropriada
		statement.setClosingBalance(saldoCompare);
		
		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento  sempre do ltimo dia do ms anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do ms de competncia.
		 */
		Calendar c = new GregorianCalendar();
		c.setTime(statement.getStatementItemList().get(0).getMovementDate());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		statement.getStatementItemList().get(0).setMovementDate(c.getTime());
	}

	private void importarExtratoDoCartaoDeCredito(StatementEntity statement) throws ParseException
	{
		int index = 0;

		/*
		 * Verifica se houve compra internacional e, em caso afirmativo,
		 * armazena a taxa de converso e uma varivel para uso posterior.
		 */
		while (this.fileContentList.get(index).indexOf("convertido") == -1)
			index++;

		index++;
		index++;


		BigDecimal taxaConversao = this.toUsaNumberFormat(this.fileContentList.get(index).substring(55, 63).trim());

		/*
		 * Reiniciando a leitura do arquivo
		 */
		index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar a string "Vencimento"
		 * que significam as datas de vencimento e pagamento dos itens do extrato.
		 */
		while (this.fileContentList.get(index).indexOf("Vencimento") == -1)
			index++;

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date dataVencimento = df.parse(this.fileContentList.get(index).substring(18, 28));

		/*
		 * Lendo linha por linha do arquivo para identificar a string
		 * "Total da fatura" que significa o valor total da fatura.
		 */
		while (this.fileContentList.get(index).indexOf("Total da fatura") == -1)
			index++;

		String auxSaldoFinal = this.fileContentList.get(index).substring(21).trim();
		auxSaldoFinal = auxSaldoFinal.replace(".", "");
		auxSaldoFinal = auxSaldoFinal.replace(",", ".");
		statement.setClosingBalance(new BigDecimal(auxSaldoFinal));

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "SALDO FATURA ANTERIOR",
		 * que significa a primeira linha relevante do arquivo e que contm o valor inicial do extrato.
		 */
		while (this.fileContentList.get(index).indexOf("SALDO FATURA ANTERIOR") == -1)
			index++;

		/*
		 * Incio do processo de captura de dados
		 */
		String openingBalanceAux = this.fileContentList.get(index).substring(55, 68).trim();
		openingBalanceAux = openingBalanceAux.replace(".", "");
		openingBalanceAux = openingBalanceAux.replace(",", ".");
		statement.setOpeningBalance(new BigDecimal(openingBalanceAux));

		StatementItemEntity statementItem = null;
		statement.setStatementItemList(new ArrayList<>());

		/* Regra:
		 * Identificar o saldo da fatura anterior como sendo o primeiro item do extrato
		 */
		statementItem = new StatementItemEntity();

		statementItem.setMovementDate(dataVencimento);
		statementItem.setDescription(this.fileContentList.get(index).substring(9, 48).trim());

		BigDecimal valorReal = this.toUsaNumberFormat(this.fileContentList.get(index).substring(55, 68).trim());
		BigDecimal valorDolar = this.toUsaNumberFormat(this.fileContentList.get(index).substring(69, 80).trim());

		BigDecimal valorTotal = valorDolar.multiply(taxaConversao);
		valorTotal = valorTotal.add(valorReal);
		statementItem.setMovementValue(valorTotal);

		if (statementItem.getMovementValue().doubleValue() >= 0.0)
			statementItem.setOperationType("D");
		else
			statementItem.setOperationType("C");

		statement.getStatementItemList().add(statementItem);

		/* Regra:
		 * Identificar os itens que compem o extrato e adicionar  lista principal
		 */
		while (this.fileContentList.get(index).indexOf("         Total") == -1)
		{
			if (!this.fileContentList.get(index).substring(0, 1).trim().equals(""))
			{
				statementItem = new StatementItemEntity();

				statementItem.setMovementDate(dataVencimento);
				statementItem.setDescription(this.fileContentList.get(index).substring(9, 48).trim());

				valorReal = this.toUsaNumberFormat(this.fileContentList.get(index).substring(55, 68).trim());
				valorDolar = this.toUsaNumberFormat(this.fileContentList.get(index).substring(69, 80).trim());

				valorTotal = valorDolar.multiply(taxaConversao);
				valorTotal = valorTotal.add(valorReal);

				// Detalhe: verificar se o valor total vai gerar um movimento de dbito ou de crdito
				if (valorTotal.compareTo(new BigDecimal(0)) >= 0)
					statementItem.setOperationType("D");
				else
					statementItem.setOperationType("C");

				// Detalhe: verificar se o valor total  negativo e, em caso afirmativo, transform-lo para positivo
				if (valorTotal.compareTo(new BigDecimal(0)) < 0)
					valorTotal = valorTotal.multiply(new BigDecimal(-1));

				statementItem.setMovementValue(valorTotal);

				statement.getStatementItemList().add(statementItem);
			}

			index++;
		}
	}

	private BigDecimal toUsaNumberFormat(String valor) {
		boolean isBraFormat = false;

		String[] temp = valor.split("");
		for (int i = 0; i < temp.length; i++)
		{
			if (temp[i].equals(","))
			{
				isBraFormat = true;
				i = temp.length;
			}
		}

		String aux = valor;

		if (isBraFormat)
		{
			aux = aux.replaceAll(",", "@");
			aux = aux.replaceAll("\\.", "");
			aux = aux.replaceAll("@", ".");
		}

		return new BigDecimal(aux);
	}
}
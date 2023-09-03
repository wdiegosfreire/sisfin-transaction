package br.com.dfdevforge.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import br.com.dfdevforge.common.enums.*;

public class DateUtils {
	protected  DateUtils() {}

	private LocalDate toLocalDate(Date dateToConvert) {
		Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		return localDateTime.toLocalDate();
	}

	private LocalDateTime toLocalDateTime(Date dateToConvert) {
		Instant instant = Instant.ofEpochMilli(dateToConvert.getTime());
		
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}

	/**
	 * <p>Tem a finalidade de calcular a quantidade de dias entre duas datas, de acordo com as regras descritas a seguir:</p>
	 * 
	 * <ul>
	 * 	<li>Tanto a data inicial quanto a final sao consideradas para o caculo;</li>
	 * 	<li>Horas, minutos e segundos serao desprezados do calculo;</li>
	 * 	<li>Quando a data inicial for igual a data final, o resultado sera <i>um</i>;</li>
	 * 	<li>Se a data inicial for posterior a data final, entao o resultado sera <i>zero</i>;</li>
	 * 	<li>Metodo <i>null safe</i>.</li>
	 * </ul>
	 * 
	 * Exemplos:
	 * <ul>
	 * 	<li>2019-07-01 - 2019-07-31 = 31 dias</li>
	 * 	<li>2019-07-01 - 2019-07-01 =  1 dias</li>
	 * 	<li>2019-07-31 - 2019-07-01 =  0 dias</li>
	 * </ul>
	 * 
	 * @param dataMenor data inicial do intervalo a ser calculado
	 * @param dataMaior data final do intervalo a ser calculado
	 * 
	 * @return quantidade de dias do intervalo de datas informado
	 */
	public Integer getDaysBetween(Date dataMenor, Date dataMaior) {
		if (dataMenor == null || dataMaior == null)
			return null;

		Long aux = ChronoUnit.DAYS.between(toLocalDate(dataMenor), toLocalDate(dataMaior)) + 1;

		return (aux > 0 ? aux.intValue() : 0);
	}

	/**
	 * <p>Tem a finalidade de converter uma data armazenada em uma <i>String</i> para um objeto <i>java.util.Date</i>, de acordo com as regras descritas a seguir:</p>
	 * 
	 * <ul>
	 *   <li>Se a data a ser convertida for nula, entao retorna <i>null</i>;</li>
	 *   <li>Se o enum de padroes de data for nulo, entao a conversao sera no padrao <i>dd/MM/yyyy</i>;</li>
	 *   <li>Metodo <i>null safe</i>.</li>
	 * </ul>
	 * 
	 * @param dateString data armazenada em String a ser convertida
	 * @param datePatternEnum enum de padroes de data
	 * 
	 * @return objeto <i>Date</i> convertido a partir da <i>String</i> fornecida
	 */
	public Date toDateFromString(String dateString, DatePatternEnum datePatternEnum) throws ParseException {
		if (dateString == null)
			return null;

		if (datePatternEnum == null)
			datePatternEnum = DatePatternEnum.PT_BR_BARS_DIA_MES_ANO;

		DateFormat dateFormat = new SimpleDateFormat(datePatternEnum.getPattern());

		return dateFormat.parse(dateString);
	}

	/**
	 * <p>Tem a finalidade de converter uma data armazenada em uma <i>String</i> para um objeto <i>java.util.Date</i>, de acordo com as regras descritas a seguir:</p>
	 * 
	 * <ul>
	 *   <li>O metodo identifica de forma automatica a mascara da data a ser convertida;</li>
	 *   <li>Se o enum de padroes de data for nulo, entao a conversao sera no padrao <i>dd/MM/yyyy</i>;</li>
	 *   <li>Metodo <i>null safe</i>.</li>
	 * </ul>
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public Date toDateFromString(String dateString) throws ParseException {
		if (dateString == null)
			return null;

		return this.toDateFromString(dateString, DatePatternEnum.getEnumByValue(dateString));
	}

	/**
	 * <p>Tem a finalidade de adicionar dias a uma determinada data.</p>
	 * 
	 * @param date
	 * @param days
	 * 
	 * @return objeto <i>Date</i> correspondente a data informada acrescida da quantidade de dias especificada em <i>amount</i>.
	 */
	public Date plusDays(Date date, Integer amount) {
		LocalDate newDate = toLocalDate(date).plusDays(amount);

		return Date.from(newDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de adicionar meses a uma determinada data.</p>
	 * 
	 * @param date
	 * @param months
	 * 
	 * @return objeto <i>Date</i> correspondente a data informada acrescida da quantidade de meses especificada em <i>amount</i>.
	 */
	public Date plusMonths(Date date, Integer amount) {
		LocalDateTime newDate = this.toLocalDateTime(date).plusMonths(amount);

		return Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de adicionar segundos a uma determinada data.</p>
	 * 
	 * @param date
	 * @param seconds
	 * 
	 * @return objeto <i>Date</i> correspondente a data informada acrescida da quantidade de dias especificada em <i>amount</i>.
	 */
	public Date plusSeconds(Date date, Integer amount) {
		LocalDateTime newDate = this.toLocalDateTime(date).plusSeconds(amount);

		return Date.from(newDate.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de subtrair dias de uma determinada data.</p>
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return objeto <i>Date</i> correspondente a data informada subtraida da quantidade de dias especificada em <i>amount</i>.
	 */
	public Date minusDays(Date date, Integer amount) {
		LocalDate newDate = toLocalDate(date).minusDays(amount);
		
		return Date.from(newDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de recuperar o ultimo dia mo mes referente a data informada.</p>
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return objeto <i>Date</i> correspondente a data do ultimo dia do mes referente a data fornecida
	 */
	public Date getFirstDayOfMonth(Date date) {
		LocalDate localDateAux = toLocalDate(date);
		localDateAux = localDateAux.withDayOfMonth(1);

		return Date.from(localDateAux.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de recuperar o ultimo dia mo mes referente a data informada.</p>
	 * 
	 * @param date
	 * @param amount
	 * 
	 * @return objeto <i>Date</i> correspondente a data do ultimo dia do mes referente a data fornecida
	 */
	public Date getLastDayOfMonth(Date date) {
		LocalDate localDateAux = toLocalDate(date);
		localDateAux = localDateAux.withDayOfMonth(localDateAux.getMonth().length(localDateAux.isLeapYear()));
		
		return Date.from(localDateAux.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de recuperar o dia mo mes referente a data informada.</p>
	 * 
	 * @param date
	 * @return objeto <i>Integer</i> correspondente ao dia do mes referente a data fornecida
	 */
	public Integer getDayOf(Date date) {
		return this.toLocalDate(date).getDayOfMonth();
	}

	/**
	 * <p>Tem a finalidade de recuperar o mes referente a data informada.</p>
	 * 
	 * @param date
	 * @return objeto <i>Integer</i> correspondente ao mes referente a data fornecida
	 */
	public Integer getMonthOf(Date date) {
		return this.toLocalDate(date).getMonthValue();
	}

	/**
	 * <p>Tem a finalidade de recuperar o ano referente a data informada.</p>
	 * 
	 * @param date
	 * @return objeto <i>Integer</i> correspondente ao ano referente a data fornecida
	 */
	public Integer getYearOf(Date date) {
		return this.toLocalDate(date).getYear();
	}

	/**
	 * <p>Tem a finalidade de recuperar o periodo referente a data informada.</p>
	 * 
	 * @param date
	 * @return objeto <i>Integer</i> correspondente ao mes referente a data fornecida
	 */
	public Integer getPeriodOf(Date date) {
		return Integer.parseInt(this.getYearOf(date).toString() + this.getMonthOf(date).toString());
	}

	public Boolean isLeapYear(Date date) {
		Integer year = this.getYearOf(date);

		return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * <p>Tem a finalidade de retornar um objeto Date sem a referencia da hora.</p>
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Date getNewDate() {
		LocalDate localDate = this.toLocalDate(new Date());
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>Tem a finalidade de retornar um objeto Date completo, ou seja, data e hora.</p>
	 * 
	 * @return
	 */
	public Date getNewDateTime() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * <p>Tem a finalidade de verificar se <code>dateOne</code> eh menor do que <code>dateTwo</code>. </p>
	 * 
	 * <ul>
	 * 	<li>Horas, minutos e segundos serao desprezados do calculo;</li>
	 * </ul>
	 * 
	 * Exemplos:
	 * <ul>
	 * 	<li>2019-12-20          lessThan 2019-12-30          -> true</li>
	 * 	<li>2019-12-20          lessThan 2019-12-20          -> false</li>
	 * 	<li>2019-12-30          lessThan 2019-12-20          -> false</li>
	 * 	<li>2019-12-20 10:00:00 lessThan 2019-12-20 11:00:00 -> false</li>
	 * </ul>
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public Boolean lessThan(Date dateOne, Date dateTwo) {
		LocalDate localDateOne = this.toLocalDate(dateOne);
		LocalDate localDateTwo = this.toLocalDate(dateTwo);

		return (localDateOne.isBefore(localDateTwo) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * <p>Tem a finalidade de verificar se <code>dateOne</code> eh menor ou igual do que <code>dateTwo</code>. </p>
	 * 
	 * <ul>
	 * 	<li>Horas, minutos e segundos serao desprezados do calculo;</li>
	 * </ul>
	 * 
	 * Exemplos:
	 * <ul>
	 * 	<li>2019-12-20          lessEqualThan 2019-12-30          -> true</li>
	 * 	<li>2019-12-20          lessEqualThan 2019-12-20          -> true</li>
	 * 	<li>2019-12-30          lessEqualThan 2019-12-20          -> false</li>
	 * 	<li>2019-12-20 12:00:00 lessEqualThan 2019-12-20 11:00:00 -> true</li>
	 * </ul>
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public Boolean lessEqualThan(Date dateOne, Date dateTwo) {
		LocalDate localDateOne = this.toLocalDate(dateOne);
		LocalDate localDateTwo = this.toLocalDate(dateTwo);
		
		return (localDateOne.isBefore(localDateTwo) || localDateOne.isEqual(localDateTwo) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * <p>Tem a finalidade de verificar se <code>dateOne</code> eh maior do que <code>dateTwo</code>. </p>
	 * 
	 * <ul>
	 * 	<li>Horas, minutos e segundos serao desprezados do calculo;</li>
	 * </ul>
	 * 
	 * Exemplos:
	 * <ul>
	 * 	<li>2019-12-20          lessThan 2019-12-30          -> false</li>
	 * 	<li>2019-12-20          lessThan 2019-12-20          -> false</li>
	 * 	<li>2019-12-30          lessThan 2019-12-20          -> true</li>
	 * 	<li>2019-12-20 10:00:00 lessThan 2019-12-20 11:00:00 -> false</li>
	 * </ul>
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public Boolean greaterThan(Date dateOne, Date dateTwo) {
		LocalDate localDateOne = this.toLocalDate(dateOne);
		LocalDate localDateTwo = this.toLocalDate(dateTwo);

		return (localDateOne.isAfter(localDateTwo) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * <p>Tem a finalidade de verificar se <code>dateOne</code> eh maior ou igual do que <code>dateTwo</code>.</p>
	 * 
	 * <ul>
	 * 	<li>Horas, minutos e segundos serao desprezados do calculo;</li>
	 * </ul>
	 * 
	 * Exemplos:
	 * <ul>
	 * 	<li>2019-12-20          lessEqualThan 2019-12-30          -> false</li>
	 * 	<li>2019-12-20          lessEqualThan 2019-12-20          -> true</li>
	 * 	<li>2019-12-30          lessEqualThan 2019-12-20          -> true</li>
	 * 	<li>2019-12-20 12:00:00 lessEqualThan 2019-12-20 11:00:00 -> true</li>
	 * </ul>
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public Boolean greaterEqualThan(Date dateOne, Date dateTwo) {
		LocalDate localDateOne = this.toLocalDate(dateOne);
		LocalDate localDateTwo = this.toLocalDate(dateTwo);
		
		return (localDateOne.isAfter(localDateTwo) || localDateOne.isEqual(localDateTwo) ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * Tem a finalidade de formatar o valor de um <code>java.util.Date</code> em uma String no formato especificado em <code>datePatternEnum</code>
	 * 
	 * @param date
	 * @return
	 */
	public String format(Date date, DatePatternEnum datePatternEnum) {
		return new SimpleDateFormat(datePatternEnum.getPattern()).format(date);
	}
}

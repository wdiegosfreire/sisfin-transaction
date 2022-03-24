package br.com.dfdevforge.common.enums;

import org.apache.commons.text.similarity.LevenshteinDistance;

import lombok.Getter;

/**
 * @author Marcelo Rebouças Jan 27, 2016 - 10:03:01 AM
 */
public enum DatePatternEnum {
	EN_US_NONE_ANO_MES_DIA("yyyyMMdd"),

	EN_US_BARS_ANO_MES("yyyy/MM"),
	EN_US_BARS_ANO_MES_DIA("yyyy/MM/dd"),

	EN_US_DASH_ANO_MES("yyyy-MM"),
	EN_US_DASH_ANO_MES_DIA("yyyy-MM-dd"),
	EN_US_DASH_DIA_MES_ANO_HOR_MIN("yyyy-MM-dd HH:mm"),
	EN_US_DASH_DIA_MES_ANO_HOR_MIN_SEG("yyyy-MM-dd HH:mm:ss"),

	PT_BR_DASH_MES_ANO("MM-yyyy"),
	PT_BR_DASH_DIA_MES_ANO("dd-MM-yyyy"),
	PT_BR_DASH_DIA_MES_ANO_HOR_MIN("dd-MM-yyyy HH:mm"),
	PT_BR_DASH_DIA_MES_ANO_HOR_MIN_SEG("dd-MM-yyyy HH:mm:ss"),

	PT_BR_BARS_MES_ANO("MM/yyyy"),
	PT_BR_BARS_DIA_MES_ANO("dd/MM/yyyy"),
	PT_BR_BARS_DIA_MES_ANO_HOR_MIN("dd/MM/yyyy HH:mm"),
	PT_BR_BARS_DIA_MES_ANO_HOR_MIN_SEG("dd/MM/yyyy HH:mm:ss");

	private DatePatternEnum(String pattern) {
		this.pattern = pattern;
	}

	@Getter
	private String pattern;

	public static DatePatternEnum getEnumByPattern(String pattern) {
		DatePatternEnum[] datePatternValues = DatePatternEnum.values();
		DatePatternEnum datePatternReturn = null;

		for (DatePatternEnum datePatternLoop : datePatternValues) {
			if (datePatternLoop.getPattern().equals(pattern)) {
				datePatternReturn = datePatternLoop;

				break;
			}
		}

		return datePatternReturn;
	}

	/**
	 * Tem a finalidade de identificar o padrão de mascara da data recebida como parametro e retornar o <code>DatePatternEnum</code> correspondente.
	 * 
	 * @param value
	 * @return
	 */
	public static DatePatternEnum getEnumByValue(String value) {
		DatePatternEnum[] datePatternValues = DatePatternEnum.values();
		DatePatternEnum datePatternReturn = null;

		String valueCompare = value.replaceAll("[0-9]", "9");

		for (DatePatternEnum datePatternLoop : datePatternValues) {
			String patternCompare = datePatternLoop.getPattern().replaceAll("[A-Za-z0-9]", "9");
			Integer levenshteinDistance = LevenshteinDistance.getDefaultInstance().apply(valueCompare, patternCompare);

			if (levenshteinDistance == 0)
				datePatternReturn = datePatternLoop;
		}

		return datePatternReturn;
	}
}
package br.com.dfdevforge.sisfintransaction.commons.utils;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class ValueUtils {
	protected  ValueUtils() {}

	/*
	 * Validacoes para objetos do tipo String
	 */

	public boolean exists(String value) {
		return StringUtils.isNotBlank(value);
	}

	public boolean notExists(String value) {
		return StringUtils.isBlank(value);
	}

	public boolean existsAndEqualsTo(String value, String match) {
		if (StringUtils.isBlank(value) || StringUtils.isBlank(match))
			return Boolean.FALSE;

		return StringUtils.equals(value, match);
	}

	public boolean contains(String value, String match) {
		return StringUtils.contains(value, match);
	}

	/*
	 * Validacoes para objetos do tipo Map
	 */

	/**
	 * <p>Tem a finalidade de verificar se um determinado <code>map</code> possui uma determinada <code>key</code>.</p>
	 * 
	 * <p>Metodo null safe</p>
	 * 
	 * @param map
	 * @param key
	 * @return <code>true</code> se a condicao for verdadeira.
	 */
	public boolean exists(Map<String, String> map, String key) {
		if (map == null)
			return Boolean.FALSE;

		if (StringUtils.isBlank(map.get(key)))
			return Boolean.FALSE;

		return Boolean.TRUE;
	}

	public boolean existsAndEqualsTo(Map<String, String> map, String key, String value) {
		if (!this.exists(map, key))
			return Boolean.FALSE;

		if (!map.get(key).equals(value))
			return Boolean.FALSE;

		return Boolean.TRUE;
	}

	public boolean isNumber(String value) {
		return NumberUtils.isParsable(value);
	}

	public String formatToCnpj(String value) {
		String digits = this.leftPadDigits(value, 14);
		return digits == null ? value : digits.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	}

	public String formatToZipCode(String value) {
		String digits = this.leftPadDigits(value, 8);
		return digits == null ? value : digits.replaceFirst("(\\d{5})(\\d{3})", "$1-$2");
	}

	private String leftPadDigits(String value, int size) {
		String digits = StringUtils.getDigits(value);

		if (StringUtils.isEmpty(digits) || digits.length() > size)
			return null;

		return StringUtils.leftPad(digits, size, '0');
	}
}
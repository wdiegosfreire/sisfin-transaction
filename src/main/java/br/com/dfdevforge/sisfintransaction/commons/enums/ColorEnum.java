package br.com.dfdevforge.sisfintransaction.commons.enums;

import lombok.Getter;

/**
 * @author Marcelo Rebouças Jan 27, 2016 - 10:03:01 AM
 */
@Getter
public enum ColorEnum {
	RED_LIGHTEN_1("red lighten-1", "#EF5350"),
	BLUE_LIGHTEN_1("blue lighten-1", "#42A5F5"),
	LIME_LIGHTEN_1("lime lighten-1", "#D4E157"),
	ORANGE_LIGHTEN_1("orange lighten-1", "#FFA726"),
	PURPLE_LIGHTEN_1("purple lighten-1", "#AB47BC"),
	YELLOW_LIGHTEN_1("yellow lighten-1", "#FFEE58"),
	BLUE_GRAY_LIGHTEN_1("blue-grey lighten-1", "#78909C"),
	DEEP_ORANGE_LIGHTEN_1("deep-orange lighten-1", "#FF7043");
	
	private ColorEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	private String name;
	private String code;
}
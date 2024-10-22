package br.com.dfdevforge.sisfintransaction.commons.enums;

import lombok.Getter;

/**
 * @author Marcelo Rebouças Jan 27, 2016 - 10:03:01 AM
 */
@Getter
public enum ColorEnum {

	RED_LIGHTEN_1("red lighten-1", "#EF5350"),
	BLUE_LIGHTEN_1("blue lighten-1", "#42A5F5"),
	CYAN_LIGHTEN_1("cyan lighten-1", "#26C6DA"),
	LIME_LIGHTEN_1("lime lighten-1", "#D4E157"),
	PINK_LIGHTEN_1("pink lighten-1", "#EC407A"),
	TEAL_LIGHTEN_1("teal lighten-1", "#26A69A"),
	AMBER_LIGHTEN_1("amber lighten-1", "#FFCA28"),
	BROWN_LIGHTEN_1("brown lighten-1", "#8D6E63"),
	GREEN_LIGHTEN_1("green lighten-1", "#66BB6A"),
	INDIGO_LIGHTEN_1("indigo lighten-1", "#5C6BC0"),
	ORANGE_LIGHTEN_1("orange lighten-1", "#FFA726"),
	PURPLE_LIGHTEN_1("purple lighten-1", "#AB47BC"),
	YELLOW_LIGHTEN_1("yellow lighten-1", "#FFEE58"),
	BLUE_GRAY_LIGHTEN_1("blue-grey lighten-1", "#78909C"),
	LIGHT_BLUE_LIGHTEN_1("light-blue lighten-1", "#29B6F6"),
	DEEP_ORANGE_LIGHTEN_1("deep-orange lighten-1", "#FF7043"),
	DEEP_PURPLE_LIGHTEN_1("deep-purple lighten-1", "#7E57C2"),
	LIGHT_GREEN_LIGHTEN_1("light-green lighten-1", "#9CCC65");
	
	private ColorEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	private String name;
	private String code;
}
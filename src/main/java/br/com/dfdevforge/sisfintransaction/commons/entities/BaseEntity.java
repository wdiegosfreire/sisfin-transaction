package br.com.dfdevforge.sisfintransaction.commons.entities;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
	private String filter;
	private Map<String, Object> map;
	private Map<String, String> filterMap;
}
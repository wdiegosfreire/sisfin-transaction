package br.com.dfdevforge.common.entities;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {
	private String filter;
	private Map<String, Object> map;
}
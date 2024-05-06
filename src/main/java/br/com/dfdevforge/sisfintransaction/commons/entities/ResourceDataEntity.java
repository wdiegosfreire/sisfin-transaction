package br.com.dfdevforge.sisfintransaction.commons.entities;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceDataEntity {
	private String token;
	private Map<String, Object> map = new HashMap<>();
}
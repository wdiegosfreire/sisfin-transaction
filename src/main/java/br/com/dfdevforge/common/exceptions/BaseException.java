package br.com.dfdevforge.common.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends Exception {
	private static final long serialVersionUID = 1L;

	private static final String SEVERITY = "error";
	private static final String SUMMARY = "Erro no Mapeado";
	private static final String DETAIL = "Ocorreu um erro no mapeado na aplicao. Tente novamente em alguns instantes ou, se preferir, entre em contato com a equipe de desenvolvimento.";

	@Getter
	private final String message;

	public BaseException() {
		this.message = this.prepareMessage(SUMMARY, DETAIL);
	}

	public BaseException(String detail) {
		this.message = this.prepareMessage(SUMMARY, detail);
	}

	public BaseException(String summary, String detail) {
		this.message = this.prepareMessage(summary, detail);
	}

	public BaseException(List<String> messageList) {
		this.message = this.prepareMessage(SUMMARY, messageList);
	}

	public BaseException(String summary, List<String> messageList) {
		this.message = this.prepareMessage(summary, messageList);
	}

	private String prepareMessage(String summary, String detail) {
		List<String> messageList = new ArrayList<>();
		messageList.add(detail);

		Message jsonMessage = new Message();
		jsonMessage.setSeverity(SEVERITY);
		jsonMessage.setSummary(summary);
		jsonMessage.setMessageList(messageList);

		return new Gson().toJson(jsonMessage);
	}

	private String prepareMessage(String summary, List<String> messageList) {
		Message jsonMessage = new Message();
		jsonMessage.setSeverity(SEVERITY);
		jsonMessage.setSummary(summary);
		jsonMessage.setMessageList(messageList);

		return new Gson().toJson(jsonMessage);
	}



	@Getter
	@Setter
	class Message {
		private String severity;
		private String summary;
		private List<String> messageList;
	}
}
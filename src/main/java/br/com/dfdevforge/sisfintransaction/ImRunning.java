package br.com.dfdevforge.sisfintransaction;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.enums.DatePatternEnum;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;

@RestController
@RequestMapping(value = "/imrunning")
public class ImRunning {
	private static final String BREAK = "<br>";

	@Autowired
	private BuildProperties buildProperties;

	@Value("${server.port}")
	private String serverPort;

	@Value("${spring.profiles.active}")
	private String springProfilesActive;

	@Value("${spring.application.name}")
	private String springApplicationName;

	@GetMapping
	public String imRunning() {
		StringBuilder statusMessage = new StringBuilder();

		statusMessage.append("application.name: " + springApplicationName + BREAK);
		statusMessage.append("application.profile: " + springProfilesActive + BREAK);
		statusMessage.append("application.version: " + String.format("%s", this.buildProperties.getVersion()) + BREAK);
		statusMessage.append("application.now: " + Utils.date.format(new Date(), DatePatternEnum.EN_US_DASH_DIA_MES_ANO_HOR_MIN_SEG));

		return statusMessage.toString();
	}
}

package br.com.dfdevforge.sisfintransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dfdevforge.sisfintransaction.commons.enums.DatePatternEnum;
import br.com.dfdevforge.sisfintransaction.commons.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;

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
	@Operation(description = "A simple resource used to check if application is running.")
	public String imRunning() {
		StringBuilder statusMessage = new StringBuilder();

		statusMessage.append("application.name: " + springApplicationName + BREAK);
		statusMessage.append("application.profile: " + springProfilesActive + BREAK);
		statusMessage.append("application.version: " + String.format("%s", this.buildProperties.getVersion()) + BREAK);
		statusMessage.append("application.now: " + Utils.date.format(new Date(), DatePatternEnum.EN_US_DASH_DIA_MES_ANO_HOR_MIN_SEG) + BREAK + BREAK);

		boolean isDebugActivated = System.getenv("SISFIN_BACKEND_DEBUG_ACTIVATED") == null ? Boolean.FALSE : Boolean.valueOf(System.getenv("SISFIN_BACKEND_DEBUG_ACTIVATED"));

		if (isDebugActivated) {
			List<String> envList = new ArrayList<>();
			
			for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
				if (entry.getKey().startsWith("SISFIN"))
					envList.add(entry.getKey() + " : " + entry.getValue() + BREAK);
			}
			
			Collections.sort(envList);
			envList.forEach(env -> {
				statusMessage.append(env);
			});
		}

		return statusMessage.toString();
	}
}
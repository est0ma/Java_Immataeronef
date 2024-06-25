package estiam.projets.immataeronef;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("immataeronef")
public class AppConf {

	private String filename;
	
}
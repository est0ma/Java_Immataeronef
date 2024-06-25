package estiam.projets.immataeronef;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	ImmatCSVReader getImmatCSVReader() {
		return new ImmatCSVReader();
	}
	
}

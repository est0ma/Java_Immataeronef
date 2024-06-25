package estiam.projets.immataeronef;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ImmataeronefApplicationTests {

	private static final ResultMatcher statusOk = status().isOk();
	private static final ResultMatcher contentTypeJsonUtf8 = content().contentType(APPLICATION_JSON_VALUE);
	private static final ResultMatcher[] statusOkUtf8 = new ResultMatcher[] { statusOk, contentTypeJsonUtf8 };

	@Autowired
	private MockMvc mvc;
	
	@BeforeEach
	void init() throws Exception {
		MockitoAnnotations.openMocks(this).close();
	}
	
	@Test
	void testFunctionnalByDefault() throws Exception {
		mvc.perform(get("/aeronef/F-GHDD"))
				.andExpectAll(statusOkUtf8)
				.andExpect(jsonPath("$.immatriculation").value("F-GHDD"))
				.andExpect(jsonPath("$.constructeur").value("PIPER AIRCRAFT CORP."))
				.andExpect(jsonPath("$.modele").value("PA 28-161"))
				.andExpect(jsonPath("$.aerodromeAttache").value("LE PLESSIS BELLEVILLE"))
				.andExpect(jsonPath("$.dateExtract").doesNotExist())
				.andExpect(jsonPath("$.proprietaire").doesNotExist());
		
		mvc.perform(get("/constructeurs"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testEvolutionA() throws Exception {
		mvc.perform(get("/aeronef/F-GHDD"))
				.andExpectAll(statusOkUtf8)
				.andExpect(jsonPath("$.dateExtract").exists());
	}

	@Test
	void testEvolutionB() throws Exception {
		mvc.perform(get("/constructeurs"))
				.andExpectAll(statusOkUtf8);
	}

	@Test
	void testEvolutionC() throws Exception {
		mvc.perform(get("/aeronef/F-GHDD"))
				.andExpectAll(statusOkUtf8)
				.andExpect(jsonPath("$.proprietaire").exists());
	}
	
	@Test
	void testEvolutionD() throws Exception {
		mvc.perform(get("/aeronef/F-GHDD"))
				.andExpectAll(statusOkUtf8)
				.andExpect(jsonPath("$.url").exists())
				.andExpect(jsonPath("$.link").exists());
	}

}

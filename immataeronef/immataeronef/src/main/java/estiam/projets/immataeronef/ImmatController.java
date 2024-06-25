package estiam.projets.immataeronef;

import static jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ImmatController {

	@Autowired
	ImmatService immatService;

	@GetMapping("/aeronef/{immat}")
	public AeronefDTO getAeronef(@PathVariable("immat") String immat, HttpServletResponse response) {
		var result = immatService.getAeronefFromImmat(immat);

		if (result.isEmpty()) {
			response.setStatus(SC_NO_CONTENT);
			return null;
		}

		return result.get();
	}

}

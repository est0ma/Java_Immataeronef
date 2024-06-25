package estiam.projets.immataeronef;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static java.time.Duration.ofSeconds;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.json.JsonMapper;

public class PlanespottersAPI {

	public static final String PLANESPOTTERS_API_URL = "https://api.planespotters.net/pub/photos/reg";
	
	private final JsonMapper objectMapper;
	
	public PlanespottersAPI() {
		objectMapper = JsonMapper.builder()
				.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
				.build();
	}
	
	public PlanespottersResultDTO getPlane(String immat) {
		try {
			var request = HttpRequest.newBuilder()
				.uri(new URI(PLANESPOTTERS_API_URL + "/" + immat))
				.timeout(ofSeconds(5))
				.GET()
				.build();

			var planespottersAPIResult = HttpClient.newBuilder()
				.followRedirects(ALWAYS)
				.build()
				.send(request, ofString())
				.body();
			
			return objectMapper.readValue(planespottersAPIResult, PlanespottersResultDTO.class);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static record PlanespottersResultDTO(ArrayList<Photos> photos) {
	}
	
	public static record Photos(
			String id,
			Thumbnail thumbnail,
			Thumbnail thumbnail_large,
			String link,
			String photographer) {
	}
	
	public static record Thumbnail(String src) {
	}


}

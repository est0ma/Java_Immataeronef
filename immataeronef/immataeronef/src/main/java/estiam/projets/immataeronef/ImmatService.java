package estiam.projets.immataeronef;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;

@Service
public class ImmatService {

	private final ImmatCSVReader immatCSVReader;
	public static final String UNKNOWN = "unknown";
	
	public ImmatService(@Autowired ImmatCSVReader immatCSVReader, @Autowired AppConf appConf) throws CsvException, IOException {
		this.immatCSVReader = immatCSVReader;
		immatCSVReader.importFile(new File(appConf.getFilename()));
	}

	public Optional<AeronefDTO> getAeronefFromImmat(String immat) {
		var entry = immatCSVReader.getEntryByImmat(immat);
		if (entry.isEmpty()) {
			return Optional.empty();
		}
		
		var constructeur = entry.getOrDefault("CONSTRUCTEUR", UNKNOWN);
		var modele = entry.getOrDefault("MODELE", UNKNOWN);
		var aerodromeAttache = entry.getOrDefault("AERODROME_ATTACHE", UNKNOWN);
		var proprietaire = entry.getOrDefault("PROPRIETAIRE", UNKNOWN);
		
		return Optional.of(new AeronefDTO(immat, constructeur, modele, aerodromeAttache,proprietaire));
	}
	
}

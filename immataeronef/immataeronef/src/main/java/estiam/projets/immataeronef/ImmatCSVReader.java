package estiam.projets.immataeronef; // on d�clare le package de la classe

import static java.util.Arrays.asList; // on importe une m�thode statique pour convertir un tableau en liste
import static java.util.Collections.unmodifiableMap; // pour cr�er une carte non modifiable
import static java.util.Objects.requireNonNull; // pour v�rifier que l'objet n'est pas nul

import java.io.File; // import de la classe File pour passer un fichier CSV � la m�thode importFile
import java.io.FileReader; // pour lire le contenu du fichier  pass� en param�tre
import java.io.IOException; // pour signaler des probl�mes potentiels lors de la lecture du fichier
import java.util.ArrayList; // pour stocker les noms des colonnes du fichier
import java.util.HashMap; // pour stocker les enregistrements CSV sous forme de paires cl�-valeur et �galement pour stocker les entr�es du fichier avec leur cl� d'immatriculation.
import java.util.Map; // pour les structures de donn�es qui stockent les enregistrements  permettant l'utilisation de diff�rentes impl�mentations de Map, comme HashMap

import com.opencsv.CSVParserBuilder; // import de la classe CSVParserBuilder pour configurer le parseur avec des param�tres sp�cifiques comme le s�parateur de champs et le caract�re de guillemet. Cela permet de personnaliser la mani�re dont les donn�es CSV sont interpr�t�es
import com.opencsv.CSVReaderBuilder; // pour cr�er une instance de CSVReader avec le filereader et le parseur CSV configur�. Cela facilite la lecture structur�e et simplifi�e des fichiers CSV en utilisant les configurations d�finies
import com.opencsv.exceptions.CsvException; // pour g�rer et signaler des probl�mes sp�cifiques � l'analyse CSV, comme des erreurs de format ou des probl�mes lors de la lecture des donn�es CSV

public class ImmatCSVReader { // on d�clare la classe publique ImmatCSVReader

    private final Map<String, Map<String, String>> entries; // on d�clare une carte finale pour stocker les entr�es

    public ImmatCSVReader() { // constructeur de la classe
        entries = new HashMap<>(); // initialise la carte des entr�es
    }

    public void importFile(File export) throws IOException, CsvException { // la m�thode pour importer un fichier CSV
        FileReader filereader = null; // on d�cllare un filereader initialis� � null
        try {
            filereader = new FileReader(export); // initialise le filereader avec le fichier d'exportation

            final var parser = new CSVParserBuilder() // on cr�e et configure un parseur CSV
                    .withSeparator(';') // on d�finit le s�parateur de champs � ';'
                    .withQuoteChar('`'); // on d�finit le caract�re de guillemet � '`'

            final var csvReader = new CSVReaderBuilder(filereader) // on cr�e un lecteur CSV en utilisant le filereader
                    .withCSVParser(parser.build()) // on associe le parseur configur� au lecteur
                    .build(); // puis construit le lecteur

            String[] nextRecord; // d�clare un tableau de cha�nes pour les enregistrements CSV
            var header = new ArrayList<String>(); // d�clare une liste pour stocker l'en-t�te du CSV
            while ((nextRecord = csvReader.readNext()) != null) { // on lit chaque enregistrement tant qu'on a pas atteint la fin
                if (header.isEmpty()) { // si l'en-t�te est vide
                    header.addAll(asList(nextRecord)); // alors on ajoute les champs de l'enregistrement � l'en-t�te
                    continue; // puis on passe � l'it�ration suivante
                }
                var mapRow = new HashMap<String, String>(); // on d�clare une carte pour stocker les valeurs de la ligne
                for (int pos = 0; pos < nextRecord.length; pos++) { // pour chaque champ de l'enregistrement
                    mapRow.put(header.get(pos), nextRecord[pos]); // on ajoute l'entr�e � la carte avec le nom du champ et sa valeur
                }
                entries.put(nextRecord[0], mapRow); // puis on ajoute la ligne � la carte des entr�es avec la premi�re colonne comme cl�
            }
        } finally {
            if (filereader != null) { // si le filereader n'est pas nul
                filereader.close(); // on ferme le filereader pour lib�rer les ressources
            }
        }
    }

    public Map<String, String> getEntryByImmat(String immat) { // la m�thode pour obtenir une entr�e par immatriculation
        return unmodifiableMap(entries.getOrDefault(requireNonNull(immat), Map.of())); // puis on retourne une carte non modifiable de l'entr�e ou une carte vide si elle n'existe pas
    }
}

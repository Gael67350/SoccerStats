package com.polytech.soccerStats.utils;

import com.polytech.soccerStats.model.Player;
import com.polytech.soccerStats.model.Position;
import com.polytech.soccerStats.model.SoccerField;
import javafx.scene.layout.FlowPane;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe de gestion des imports CSV.
 */
public class DataImporter {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss.SS";

    private String filePath;

    public DataImporter(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Charge les enregistrements contenus dans un fichier CSV.
     *
     * @return La structure de donnée construite a partir des données du fichier
     * @throws IOException Si une erreur est rencontrée lors de la lecture de fichier
     * @see SoccerField
     */
    public SoccerField loadData() throws IOException, ParseException {
        InputStreamReader inputStream = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
        CSVParser parser = CSVFormat.EXCEL.parse(inputStream);

        SoccerField soccerField = new SoccerField();

        for (CSVRecord record : parser.getRecords()) {
            Player player = new Player(Integer.parseInt(record.get(1)));
            Date timestamp = parseTimestamp(record.get(0));

            if (!soccerField.addPlayer(player)) {
                player = soccerField.getPlayer(player.getTagId());

                if (player == null) {
                    throw new IOException("Player addition in data structure failed");
                }
            }

            float posX = Float.parseFloat(record.get(2));
            float posY = Float.parseFloat(record.get(3));

            // Normalize positions
            if (posX < SoccerField.MIN_WIDTH) {
                posX = SoccerField.MIN_WIDTH;
            } else if (posX > SoccerField.MAX_WIDTH) {
                posX = SoccerField.MAX_WIDTH;
            }

            if (posY < SoccerField.MIN_HEIGHT) {
                posY = SoccerField.MIN_HEIGHT;
            } else if (posY > SoccerField.MAX_HEIGHT) {
                posY = SoccerField.MAX_HEIGHT;
            }

            player.getPositions().add(new Position(timestamp, posX, posY, Float.parseFloat(record.get(4)), Float.parseFloat(record.get(5)), Float.parseFloat(record.get(6)), Float.parseFloat(record.get(7)), player));
        }

        return soccerField;
    }

    private Date parseTimestamp(String str) throws ParseException {
        SimpleDateFormat formatter;

        if (str.length() == 21) {
            str += "0";
        }

        if (str.length() == 22) {
            formatter = new SimpleDateFormat(DATE_FORMAT_MILLIS);
        } else {
            formatter = new SimpleDateFormat(DATE_FORMAT);
        }

        return formatter.parse(str);
    }
}

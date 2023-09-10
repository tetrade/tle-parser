package org.example;


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import org.orekit.data.DataProvidersManager;
import org.orekit.data.DirectoryCrawler;
import org.orekit.propagation.analytical.tle.TLE;

public class TLEParser {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(TLEParser.class.getName());
    }

    private final Deque<String> stringFileDeque;

    public TLEParser(File file) throws IOException {

        File orekitData = new File("src/main/resources/orekit-data-master");
        DataProvidersManager manager = DataProvidersManager.getInstance();
        manager.addProvider(new DirectoryCrawler(orekitData));

        stringFileDeque = new ArrayDeque<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringFileDeque.addLast(line);
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "File not found", e);
            throw new IOException("File not found");
        }


    }

    public TLEParser(String path) throws IOException {
        this(new File(path));
    }

    public List<TLEData> parse() throws DataFormatException {

        if (stringFileDeque.size() < 2) {
            logger.log(Level.WARNING, "File can't have less than 2 lines");
            stringFileDeque.clear();
            throw new DataFormatException("File can't have less than 2 lines");
        }

        List<TLEData> data = new ArrayList<>();

        while (stringFileDeque.size() >= 2) {
            String line1 = stringFileDeque.pollFirst();

            String line2 = stringFileDeque.pollFirst();

            if (TLE.isFormatOK(line1, line2)) {
                data.add(new TLEData(line1, line2));
            } else {
                String line3 = stringFileDeque.pollFirst();
                if (TLE.isFormatOK(line2, line3)) {
                    data.add(new TLEData(line1, line2, line3));
                } else {
                    logger.log(Level.WARNING, "Wrong file format");
                    stringFileDeque.clear();
                    throw new DataFormatException("Wrong file format");
                }
            }
        }

        return data;
    }
}

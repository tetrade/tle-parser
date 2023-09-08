package org.example;

import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.zip.DataFormatException;

public class Main {
    public static void main( String[] args ) throws DataFormatException, IOException {


        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            TLEParser tleParser = new TLEParser(file);
            List<TLEData> data = tleParser.parse();

            for (TLEData tle : data) { System.out.println(tle); }
        }

    }
}

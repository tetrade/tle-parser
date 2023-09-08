package org.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.assertj.core.api.Assertions.*;



class MainTest {

    @Test
    void shouldReturnEmptyList_whenFileEmpty() throws DataFormatException, IOException {
        TLEParser parser = new TLEParser("test-files/empty-file1.txt");
        assertThat(parser.parse()).isEmpty();
    }

    @Test
    void shouldThrowException_whenFileHaveWrongFormat1() throws IOException {
        TLEParser parser = new TLEParser("test-files/wrong-format-file1.txt");
        assertThatThrownBy(parser::parse)
                .isInstanceOf(DataFormatException.class)
                .hasMessage("Wrong file format");
    }

    @Test
    void shouldThrowException_whenFileHaveWrongFormat2() throws IOException {
        TLEParser parser = new TLEParser("test-files/wrong-format-file2.txt");
        assertThatThrownBy(parser::parse)
                .isInstanceOf(DataFormatException.class)
                .hasMessage("Wrong file format");
    }

    @Test
    void shouldThrowException_whenFileNotFound() throws IOException {
        assertThatThrownBy(() -> new TLEParser("test-files/file-dont-exist.txt"))
                .isInstanceOf(IOException.class)
                .hasMessage("File not found");
    }

    @Test
    void shouldSuccessfullyParseFile_whenFileRightFormatWithNames() throws IOException, DataFormatException {
        TLEParser parser = new TLEParser("test-files/celestrak.org_NORAD_elements_gp.php_SPECIAL=gpz&FORMAT=tle.txt");
        List<TLEData> result = parser.parse();


        BufferedReader bufferedReader = new BufferedReader(new FileReader("test-files/celestrak.org_NORAD_elements_gp.php_SPECIAL=gpz&FORMAT=tle.txt"));
        String line;
        List<String> expected = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
           expected.add(line);
        }

        for (int i = 0; i < expected.size(); i += 3) {
            assertThat(expected.get(i)).isEqualTo(result.get(i / 3).getName());
            assertThat(expected.get(i + 1)).isEqualTo(result.get(i / 3).getLine1());
            assertThat(expected.get(i + 2)).isEqualTo(result.get(i / 3).getLine2());
        }
    }

    @Test
    void shouldSuccessfullyParseFile_whenFileRightFormatWithoutNames() throws IOException, DataFormatException {
        TLEParser parser = new TLEParser("test-files/celestrak.org_NORAD_elements_gp.php_GROUP=spire&FORMAT=tle.txt");
        List<TLEData> result = parser.parse();


        BufferedReader bufferedReader = new BufferedReader(new FileReader("test-files/celestrak.org_NORAD_elements_gp.php_GROUP=spire&FORMAT=tle.txt"));
        String line;
        List<String> expected = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            expected.add(line);
        }

        for (int i = 0; i < expected.size(); i += 2) {
            assertThat(result.get(i / 2).getName()).isNull();
            assertThat(expected.get(i)).isEqualTo(result.get(i / 2).getLine1());
            assertThat(expected.get(i + 1)).isEqualTo(result.get(i / 2).getLine2());
        }
    }


    @Test
    void shouldSuccessfullyParseFile_whenFileRightFormatWithAndWithOutNames() throws IOException, DataFormatException {
        TLEParser parser = new TLEParser("test-files/test-file3.txt");
        List<TLEData> result = parser.parse();


        BufferedReader bufferedReader = new BufferedReader(new FileReader("test-files/test-file3.txt"));
        String line;
        List<String> expected = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            expected.add(line);
        }

        for (int i = 0; i < 9; i += 3) {
            assertThat(expected.get(i)).isEqualTo(result.get(i / 3).getName());
            assertThat(expected.get(i + 1)).isEqualTo(result.get(i / 3).getLine1());
            assertThat(expected.get(i + 2)).isEqualTo(result.get(i / 3).getLine2());
        }

        for (int i = 9; i < expected.size(); i += 2) {
            assertThat(result.get(3 + (i - 9) / 2).getName()).isNull();
            assertThat(expected.get(i)).isEqualTo(result.get(3 + (i - 9) / 2).getLine1());
            assertThat(expected.get(i + 1)).isEqualTo(result.get(3 + (i - 9) / 2).getLine2());
        }
    }
}

package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IPFromFileFactory {

    public static String convertFile(final String filePath) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        final String addr = br.readLine();
        br.close();
        return addr;
    }

}
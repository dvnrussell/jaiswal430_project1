package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class FileFactory {

    public static String convertAddressFile(final String filePath)
    throws IOException {
        final BufferedReader br = 
            new BufferedReader(new FileReader(new File(filePath)));
        final String addr = br.readLine();
        br.close();
        return addr;
    }

    public static void writeBytesToFile(final byte[] data, final String fName)
    throws IOException {
        File file = new File (fName);
        OutputStream oStream = new FileOutputStream(file);
        oStream.write(data);
        oStream.close();
    }

}
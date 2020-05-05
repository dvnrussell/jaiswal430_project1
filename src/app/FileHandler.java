package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

    public static String convertAddressFile(final String fName)
    throws IOException {
        final BufferedReader br = 
            new BufferedReader(new FileReader(new File(fName)));
        final String addr = br.readLine();
        br.close();
        return addr;
    }

    public static void writeBytesToFile(final byte[] data, final String fName)
    throws IOException {
        final File file = new File(fName);
        final OutputStream oStream = new FileOutputStream(file);
        oStream.write(data);
        oStream.close();
    }

    public static byte[] readBytesFromFile(final String fName) 
    throws IOException {
        final File file = new File(fName);
        final InputStream iStream = new FileInputStream(file);
        final byte[] data = iStream.readAllBytes();
        iStream.close();
        return data;
    }
}
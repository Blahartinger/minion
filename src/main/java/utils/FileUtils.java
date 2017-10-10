package utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FileUtils {
    public static String resourceFileToString(String filename) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            BufferedInputStream bis = new BufferedInputStream(classLoader.getResourceAsStream(filename));
            int result = 0;
            result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return buf.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

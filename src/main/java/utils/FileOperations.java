package utils;

import java.io.*;

public class FileOperations {


    private  static String path;

    public FileOperations(String path) {
        this.path = path;
    }

    public static boolean readCsvFile() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Commons.addRoute(values[0], values[1], values[2]);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static Boolean addToCsv(String src, String dst, String weight) {
        try (OutputStream out = new FileOutputStream(path,true)) {
            out.write((src + "," + dst + "," + weight+"\n").getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

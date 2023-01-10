package Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LineCounterThread implements Runnable{
    String fileName;
    int lines;

    public LineCounterThread(String fileName) {
        this.fileName = fileName;
    }

    /**
     * it counts the number of lines the file has
     * */
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

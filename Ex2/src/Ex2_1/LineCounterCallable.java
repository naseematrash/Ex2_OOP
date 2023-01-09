package Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class LineCounterCallable implements Callable<Integer> {
    String fileName;

    public LineCounterCallable(String fileName) {
        this.fileName = fileName;
    }

    public Integer call() throws Exception {
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                lines++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}